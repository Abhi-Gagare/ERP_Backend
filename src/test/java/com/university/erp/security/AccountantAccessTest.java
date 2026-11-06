package com.university.erp.security;

import com.university.erp.config.SecurityConfig;
import com.university.erp.controller.AuthController;
import com.university.erp.controller.finance.FinanceOverviewController;
import com.university.erp.service.AuthService;
import com.university.erp.service.FeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({FinanceOverviewController.class, AuthController.class})
@Import({SecurityConfig.class, JwtAuthFilter.class})
class AccountantAccessTest {
    @Autowired MockMvc mvc;
    @MockBean JwtService jwtService;
    @MockBean UserDetailsService userDetailsService;
    @MockBean FeeService feeService;
    @MockBean AuthService authService;

    @Test void financeRequiresAuthentication() throws Exception {
        mvc.perform(get("/api/finance/overview")).andExpect(status().isUnauthorized());
        mvc.perform(get("/api/auth/me")).andExpect(status().isUnauthorized());
    }

    @Test void accountantAndAdminCanReadOverview() throws Exception {
        for (String role : new String[]{"ACCOUNTANT", "ADMIN"}) {
            mvc.perform(get("/api/finance/overview").with(user("staff").roles(role)))
                .andExpect(status().isOk());
        }
    }

    @Test void otherRolesCannotReadFinance() throws Exception {
        for (String role : new String[]{"STUDENT", "FACULTY"}) {
            mvc.perform(get("/api/finance/overview").with(user("other").roles(role)))
                .andExpect(status().isForbidden());
        }
    }

    @Test void accountantCannotUseAcademicOrLegacyWriteApis() throws Exception {
        for (String path : new String[]{"/api/students", "/api/admissions", "/api/courses", "/api/admin/users"}) {
            mvc.perform(get(path).with(user("accountant").roles("ACCOUNTANT")))
                .andExpect(status().isForbidden());
        }
        mvc.perform(post("/api/fees/FEE-1/pay").with(user("accountant").roles("ACCOUNTANT")))
            .andExpect(status().isForbidden());
    }

    @Test void staffProvisioningRequiresAdmin() throws Exception {
        for (String role : new String[]{"ACCOUNTANT", "ADMIN", "FACULTY"}) {
            String body = "{\"email\":\"staff@example.com\",\"password\":\"Example123!\",\"fullName\":\"Staff\",\"role\":\"" + role + "\"}";
            mvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isForbidden());
            mvc.perform(post("/api/auth/register").with(user("accountant").roles("ACCOUNTANT"))
                .contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isForbidden());
            mvc.perform(post("/api/auth/register").with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isCreated());
        }
    }

    @Test void studentRegistrationRemainsAvailable() throws Exception {
        mvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"student@example.com\",\"password\":\"Example123!\",\"fullName\":\"Student\",\"role\":\"STUDENT\"}"))
            .andExpect(status().isCreated());
    }
}

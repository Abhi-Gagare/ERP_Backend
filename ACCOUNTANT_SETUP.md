# Accountant portal foundation

The portal uses the existing users, JWT authentication, PostgreSQL database and
FeeService. No separate student or accountant credential table is introduced.

## Existing databases

Back up the database and review its users role constraints. For the standard
schema, apply `src/main/resources/db/manual/001_add_accountant_role.sql` before
creating Accountant users. This is a manual migration, not automatically run by
Spring. It preserves all user rows and existing role values. If a deployment has
custom role constraints, coordinate an equivalent additive change with its owner.

Fresh databases use the updated entity/schema role values.

## Create an Accountant

For a fresh database, provision the first Admin using the bootstrap environment
variables documented in README.md. Existing Admin accounts are unchanged.

An authenticated ADMIN can POST to `/api/auth/register` with email, fullName,
password and role `ACCOUNTANT`. Send the Admin access token as a Bearer token.
Use a unique password; no default Accountant credential is seeded or committed.
Public registration remains available for STUDENT only. Staff registration now
requires ADMIN; coordinate this shared security change with the Admin developer.

Sign in at `/accountant/login` using the same `/api/auth/login` endpoint.
The regular university login also sends Accountant users to `/accountant`.

## Access boundaries

`/api/finance/**` is restricted to ACCOUNTANT or ADMIN. Only the read-only
`GET /api/finance/overview` endpoint is implemented in this milestone. Accountant
users cannot use other `/api/**` module endpoints, apart from shared auth routes.
Admin, Faculty and Student retain their existing module access rules.

The overview shows actual all-time billed/collected totals and fee counts. It does
not infer collectible debt from amounts that may include waived fees. Collection,
receipt, refund, approval and ledger screens are future milestones.

Access tokens are required for authenticated requests; refresh tokens are accepted
only by the refresh flow. The backend enforces access independently of browser
storage and frontend navigation. The frontend keeps valid sessions on a 403.

## Checks

Run `mvn test` for role/access regression tests. Run `npx tsc --noEmit` and ESLint
on the changed frontend files. Integration checks should cover Admin and Accountant
login, `/api/auth/me`, overview access and denial of academic/write APIs.

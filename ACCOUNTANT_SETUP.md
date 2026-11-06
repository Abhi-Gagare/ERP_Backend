# Accountant portal

Uses the shared users, JWT authentication, PostgreSQL database, admissions and
fees. No separate student database or default Accountant password is created.

## Setup

1. Configure the environment variables in [README.md](README.md).
2. Review and apply the numbered SQL files in `src/main/resources/db/manual`
   in order. They are manual migrations; SQL initialization is disabled.
   Run with autocommit: some indexes use `CREATE INDEX CONCURRENTLY`.
3. An Admin creates an Accountant through user management. See
   [Admin users and email](ADMIN_USERS_AND_EMAIL.md) for invitations and SMTP.
4. Sign in at `/accountant/login`. The regular login also redirects Accountants.

Existing installations must review their role constraints before applying 001.
Hibernate schema update is for development; coordinate shared database changes
with the team. Preserve signing/encryption keys outside Git.

## Implemented modules

| Page under `/accountant` | Purpose |
| --- | --- |
| `/` | Fee totals, aging, recent charges and upcoming dues |
| `/fees` | Shared admission fee accounts, individual and bulk assignment |
| `/fee-structures` | Draft, publish and archive fee/installment templates |
| `/collections` | Verified manual collections and immutable receipts |
| `/defaulters` | Outstanding/overdue accounts, follow-ups and reminder drafts |
| `/scholarships` | Scholarship/concession applications and document verification |
| `/refunds` | Receipt-based requests and verified refund payouts |
| `/income-expenses` | Non-fee income and operating expenses |
| `/approvals` | Institution-funded concession decisions and postings |
| `/approvals/refunds` | Refund approval queue |
| `/approvals/operations` | Income/expense approval queue |

General Ledger, Budget, Payroll/Vendors, Reports, Accountant Settings and the
central Audit Logs page remain placeholders. Financial records retain their own
actors, timestamps, reasons and references.

## API groups

All routes below start with `/api/finance` and require ADMIN or ACCOUNTANT.
Accountants cannot access unrelated academic/Admin APIs.

| Prefix | Operations |
| --- | --- |
| `/dashboard` | Read fee aggregates and bounded attention lists |
| `/fees` | Search charges, approved admissions and shared fee accounts; assign |
| `/fee-structures` | Course lookup, template creation and status transitions |
| `/fee-assignments` | Frozen previews and resumable bulk assignment |
| `/collections` | Collect, list payments, select charges and read receipts |
| `/defaulters` | Worklist, follow-up history and manual reminder drafts |
| `/scholarships` | Applications and document review |
| `/approvals/awards` | Pending/history, approve allocations or decline |
| `/refunds` | Receipt capacity, requests, review and payout recording |
| `/operations` | Categories, entries, review, settlement and filtered totals |

Operational lists use 20-row pages. Mutation request UUIDs support safe retries;
reuse the same payload and actor after an uncertain response. Changed retries
conflict. Shared fee rows lock consistently during payments, concessions and
refunds. Bulk assignment uses frozen selections and bounded transactions.

## Financial rules

- Collectible balance = max(original fee − concessions − net paid, 0), excluding
  waived charges. Overdue means due before today; due today is not overdue.
  Financial dates default to `Asia/Kolkata` via `app.finance.time-zone`.
- Fee DTOs expose `concessionAmount`, account summaries `concessions`, and the
  dashboard `totalConcessions` and `totalRefunded`. `amountPaid` is net of paid
  refunds. Stored PAID means settled, including concession-funded settlement.
- Gross charges and original receipts remain unchanged. Refund payouts reduce
  net paid and reopen debt; they do not cancel the original charge. Existing
  waived charges remain waived. Reconcile gross receipt history with paid
  refunds when producing reports.
- A separate finance staff member must review applications, refunds and
  operating entries. The creator may execute an approved manual settlement;
  there is no third-person rule or per-amount approval hierarchy yet.
- Verified institution-funded awards allocate only against their student's
  unpaid fees, capped by the requested amount and current balances. External
  sponsor funding requires a separate reconciliation workflow.
- Pending/approved/paid refunds reserve the original receipt allocation.
  Rejection or withdrawal releases capacity. Legacy payments without a finance
  receipt are not refundable through this workflow.
- Operating entries cover non-fee cash only. Do not re-enter student fees,
  refunds, sponsor fee funding, payroll or vendor invoice payments. Totals count
  SETTLED entries across all matching rows; date filters use document dates.
  These are cash totals, not accrual profit/loss or double-entry journals.
- Collection, refund and operating settlement screens record verified manual
  transactions; they do not transfer money. Reminder drafts do not send mail.
- Final decisions/postings cannot be edited through these APIs. Reversals,
  partial operating settlements, gateway integration, bank reconciliation and
  automatic tax/budget calculations remain pending.

## Team coordination

Keep existing `admissions` and `fees` as the source of student fee records.
Admission records do not yet have a normalized student/course enrollment link.
Coordinate shared fee DTO/balance rules, eligibility, document storage, scheme
ownership and finance-role permissions with Admin/Admissions developers.
The current schema has no institution/tenant key; multi-college isolation needs
a shared tenant model before deployment. Future ledger work should consume
immutable source IDs once rather than duplicating financial transactions.

## Checks and Git

Backend: `mvn test` and `mvn package`.
Frontend: `npx tsc --noEmit` and ESLint on the Accountant routes/components/libs.
Financial changes should also verify live balances, concurrent retries, separate
review, rollback and receipt preservation. Keep meaningful regression tests and
all required migrations in Git; exclude credentials, databases, build output,
screenshots and local runtime logs.

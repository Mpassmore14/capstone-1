# LedgerApp

## Overview
LedgerApp is a simple Java-based financial ledger application that allows users to record deposits and payments, view transactions, and generate financial reports.

## Features
- Add deposits and payments
- Save transactions to a CSV file
- Load existing transactions from file
- View all transactions, deposits only, and payments only
- Generate financial reports:
    - Month-to-date
    - Previous month
    - Year-to-date
    - Previous year
    - Search by vendor

## How to Use
1. Run the application.
2. Use the menu options to:
    - Add deposits (`D`)
    - Make payments (`P`)
    - Display the ledger (`L`)
    - Access additional ledger options (`M`)
    - Exit the application (`X`)
3. Access reports through the Ledger Menu.

## File Storage
Transactions are stored in `Transactions.csv`, using a pipe (`|`) delimiter.

## To-Do
- Implement vendor search
- Optimize file handling
- Improve user interface experience

## Notes
This application is built using Java and requires a compatible runtime environment to execute.

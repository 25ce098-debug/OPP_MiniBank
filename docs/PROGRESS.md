# MiniBank — Practical Progress

## Practical 1 — MiniBank Project Setup and Menu
- **Status:** ✅ Complete
- **Files:** `src/MiniBank.java`, `src/BankInfo.java`, `src/MenuOption.java`
- **Concepts:** Record, enum, switch expression, Scanner input, input validation
- **Compilation:** ✅ Successful
- **Testing:** ✅ Menu loops correctly, placeholders display, invalid input handled, EXIT works
- **Tag:** `lab-01`

## Practical 2 — Customer and Account Classes
- **Status:** ✅ Complete
- **Files:** `src/Customer.java`, `src/Account.java`, `src/MiniBank.java` (updated)
- **Concepts:** Encapsulation, private fields, public getters, static counter, constructor chaining with `this()`, `final` accountNumber
- **Compilation:** ✅ Successful
- **Testing:** ✅ Deposit adds to balance, withdraw returns true/false correctly, balance cannot be set from outside
- **Tag:** `lab-02`

## Practical 3 — Object Methods, Nested Classes and Cloning
- **Status:** ✅ Complete
- **Files:** `src/Account.java` (toString, equals, hashCode), `src/Customer.java` (Address nested class, Cloneable, clone)
- **Concepts:** toString, equals/hashCode contract, static nested class, Cloneable, clone(), instanceof
- **Compilation:** ✅ Successful
- **Testing:** ✅ toString readable, equals works by accountNumber, Address nested class, clone creates separate copy
- **Tag:** `lab-03`

## Practical 4 — Input Validation and Command Parsing
- **Status:** ✅ Complete
- **Files:** `src/Validator.java`, `src/TransactionType.java`, `src/Command.java`, `src/CommandParser.java`, `src/StatementFormatter.java`
- **Concepts:** Compiled Pattern/regex, String.split(), StringBuilder, records
- **Compilation:** ✅ Successful
- **Testing:** ✅ Validators return correct true/false, parser extracts command fields, invalid input throws exception
- **Tag:** `lab-04`

## Practical 5 — Inheritance and Polymorphism
- **Status:** ✅ Complete
- **Files:** `src/Account.java` (made abstract), `src/SavingsAccount.java`, `src/CurrentAccount.java`, `src/FixedDepositAccount.java`, `src/MiniBank.java` (updated)
- **Concepts:** Abstract class, abstract methods (`interestRate()`, `canWithdraw()`), `super()` constructor chaining, subtype-specific withdrawal rules, runtime polymorphism, pattern matching with `instanceof`
- **Compilation:** ✅ Successful
- **Testing:** ✅ Dynamic dispatch on `Account[]` returns 4.0%, 0.0%, 7.0%; subtype withdrawal rules verified (Savings min-balance check, Current overdraft, FD locked)
- **Tag:** `lab-05`

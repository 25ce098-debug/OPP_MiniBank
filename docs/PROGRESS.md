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

## Practical 6 — Interfaces, Lambdas, Packages and JAR
- **Status:** ✅ Complete
- **Files:** `src/model/Transactable.java`, `src/model/InterestBearing.java`, `src/model/WithdrawRule.java`, `src/model/Premium.java`, `src/service/AccountService.java`, all models moved to `model`, utils moved to `util`, `Manifest.txt`, `src/MiniBank.java` (updated)
- **Concepts:** Interfaces (`Transactable`), default methods (`InterestBearing.yearlyInterest()`), functional interfaces & lambdas vs anonymous class (`WithdrawRule`), marker interface (`Premium`), packages (`model`, `util`, `service`), static import (`Validator.*`), building & executing runnable JAR
- **Compilation:** ✅ Successful
- **Testing:** ✅ `java -jar minibank.jar` runs successfully; anonymous class and lambda match outputs; default method computes interest accurately; marker interface detected
- **Tag:** `lab-06`

## Practical 7 — Custom Annotations and Reflection
- **Status:** ✅ Complete
- **Files:** `src/model/annotation/Id.java`, `src/model/annotation/Positive.java`, `src/model/annotation/MaxLength.java`, `src/util/AnnotationValidator.java`, `src/model/Account.java` (annotated), `src/model/Customer.java` (annotated), `src/MiniBank.java` (updated)
- **Concepts:** Standard annotations (`@Override`, `@FunctionalInterface`), custom annotations (`@Id`, `@Positive`, `@MaxLength`), meta-annotations (`@Target`, `@Retention(RUNTIME)`), Java reflection (`getDeclaredFields()`, `isAnnotationPresent()`, `setAccessible()`, `get()`), validating inherited fields
- **Compilation:** ✅ Successful
- **Testing:** ✅ Account with negative balance correctly produces `must be > 0`; valid account returns 0 errors; `@MaxLength` flag triggers on overflow
- **Tag:** `lab-07`

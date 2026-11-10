# Lesson: Exceptions

The Java programming language uses *exceptions* to handle errors and other exceptional events.
This lesson describes when and how to use exceptions.

## What Is an Exception?

Definition: **An *exception* is an event, which occurs during the execution of a program, 
that disrupts the normal flow of the program's instructions.**

> The term *exception* is shorthand for the phrase "exceptional event".

1. When an error occurs within a method, the method crates an object and hands it off to
the runtime system.
   - The object, called an *exception object*, contains information about the error,
   including is type and the state of the program when the error occurred.
2. Once the exception object is created, it is handed to the runtime system, this
is called **throwing an exception**.
3. After a method throws an exception, the runtime system attempts to find something 
to handle it.
   - The set of possible "somethings" to handle the exception is the ordered list
   of methods that had been called to get to the method where the error occurred.
   - This list of methods is known as the *call stack*.
4. The runtime system searches the *call stack* for a method that contains a block of
code that can handle the exception.
   - This block of code is called an *exception handler*.
   - The search begins with the method in which the error occurred and proceeds
   through the call stack in the reverse order in which the methods were called.
5. When an appropriate handler is found, the runtime system passes the exception
to the handler.
   - An exception handler is considered appropriate if the type of the exception
   object thrown matches the type that can be handled by the handler.
   - The exception handler chosen is said to *catch the exception*.
6. If the runtime system exhaustively searches all the methods on the call stack
without finding an appropriate exception handler, the runtime systema (and,
consequently the program) terminates.

## Advantages of Exceptions

## The Catch or Specify Requirement

Valid Java programming language code must honor the *Catch or Specify Requirement*.
This means that code that might throw certain exceptions must be enclosed by either
of the following:

- **A `try` statement that catches the exception**. The `try` must provide a handler
for the exception.
- **A method that specifies that it can throw the exception**. The method must
provide a `throws` clause that lists the exception.

Code that fails to honor the Catch or Specify Requirement will not compile.

Not all exceptions are subject to the *Catch or Specify Requirement*.
To understand why, we need to look at the three basic categories of exceptions,
only one of which is subject to the Requirement.

## The Three Kinds Of Exceptions

### Checked Exception
The first kind of exception is the **checked exception**. These are exceptional
conditions that a well-written application should anticipate and recover from.

Checked exceptions are subject to the *Catch or Specify Requirement*.
All exceptions are checked exceptions, except for those indicated by `Error`,
`RuntimeException`, and their subclasses.

### Error
The second kind of exception is the error. These are exceptional conditions
that are external to the application, and that the application usually cannot
anticipate or recover from.

Errors are not subject to the *Catch or Specify Requirement*. Errors are those
exceptions indicated by `Error` and its subclasses.

### Runtime Exception
The third kind of exception is the runtime exception. These are exceptional
conditions that are internal to the application, and that the application
usually cannot anticipate or recover from. These usually indicate programming
bugs, such as logic errors or improper use of an API.

The application can catch runtime exceptions, but it probably makes more sense
to eliminate the bug that caused the exception to occur.

Runtime exceptions are not subject to the *Catch or Specify Requirement*.
Runtime exceptions are those indicated by `Runtime Exception` amd its subclasses.

**Errors** and **Runtime Exceptions** are collectively known as 
*unchecked exceptions*.

## Bypassing Catch or Specify
Some programmers consider the *Catch or Specify Requirement* a serious flaw in
the exception mechanism and bypass it by using unchecked exceptions in place of
checked exceptions. In general, this is not recommended.

Unchecked Exceptions controversy talks about when it is appropriate to use
unchecked exceptions.

## Catching and Handling Exceptions

To write an exception handler, you might use its three components: 
the `try`, `catch`, and `finally` blocks.

### The `try` Block
The first step in constructing an exception handler is to enclose the code
that might throw an exception within a `try` block. In general, a `try`
block looks like the following:

```
try {
   code segment with one or more lines of code that could throw an exception
}
catch and finally blocks ...
```

You can put each line of code that might throw an exception within its own
try block and provide separate exception handlers for each. Or, you can put
all the code within a single try block and associate multiple handlers with
it.

If an exception occurs within the `try` block, that exception is handled
by an exception handler associated with it. To associate an exception
handler with a `try` block, you must put a `catch` block after it.

### The `catch` Blocks
You associate exception handlers with a `try` block by providing one or
more `catch` blocks directly after the `try` block. No code can be
between the end of the `try` block and the beginning of the first `catch`
block.

```
try {
   code that could throw one or more exceptions
} catch (ExceptionType name) {
   handle exception of given type
} catch (ExceptionType name) {
   handle exception of given type
}
```

Each `catch` block is an exception handler that handles the type of 
exception indicated by its argument. The argument type, `ExceptionType`,
declares the type of exception that the handler can handle and must be
the name of a class that inherits from the `Throwable` class.

The `catch` block contains code that is executed if and when the
exception handler is invoked. The runtime system invokes the
exception handler when the handler is the first one in the call stack
whose `ExceptionType` matches the type of the exception thrown. 
The system considers it a match if the thrown object can legally be
assigned to the exception handler's argument.

Exception handlers can do more than just print error messages or halt
the program. They can do error recovery, prompt the user to make a
decision, or propagate the error up to a higher-level handler using
*chained exceptions*.

#### Multi-Catching Exceptions
You can catch more than one type of exception with one exception handler,
with the multi-catch pattern.

In Java SE 7 and later, a single `catch` block can handle more than one
type of exception. This feature can reduce code duplication and lessen
the temptation to catch an overly broad exception.

In the `catch` clause, specify the types of exceptions that block can
handle, and separate each exception type with a vertical bar `|`:

```
catch (IOException | SQLException ex) {
   handle both exceptions
}
```

Note: If a catch block handles more than one exception type, then the
`catch` parameter is implicitly `final`. Note also that the compiler
puts constraints on the exceptions you catch with this syntax, the
exceptions cannot extend one another, in any order.

### The `finally` Block
The `finally` block always executes when the `try` block exits.
This ensures that the `finally` block is executed even if an unexpected
exception occurs. But `finally` is useful for more than just exception
handling — it allows the programmer to avoid having cleanup code
accidentally bypassed by a `return`, `continue`, or `break`.
Putting cleanup code in a `finally` block is always a good practice,
even when no exceptions are anticipated.

Note: If the JVM exits while the `try` or `catch`code is being
executed, then the `finally` block may not execute.

Remember, the runtime system always executes the statements
within the `finally` block regardless of what happens within
the `try` block.

#### Important 
- **The `finally` block is a very tool for preventing
resource leaks**. When closing a file or otherwise recovering
resources, place the code in a `finally` block to ensure is
always recovered.
- Consider using the *try-with-resources* statement in these
situations, which automatically releases system resources
when no longer needed.

#### Exceptions Thrown form a `finally` Block
You may come across the following situation.

1. You execute a `try` block, and a exception is thrown,
interrupting the execution of this block.
2. You then execute your `finally` block. But then, another
exception is thrown within your `finally` block.

This can happen if the `finally` block tries to close a resource,
and an exception is thrown by the call to `close()`.

In such a case, the exception thrown in your `finally` block is
the one that is propagated to the calling code. The exception
thrown in the `try` block is discarded and forgotten.
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
# BLoCoffee
A Java translation of [Felix Angelov](https://github.com/felangel)'s [Bloc](https://github.com/felangel/bloc) library,
which is a predictable state management library that helps implement the
[BLoC design pattern](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc).

*I take no credit for this great idea.*

The goal of this project is simply to experiment with the design pattern to see how it can be applied to building
applications with Java and JavaFX, and even (Lord have mercy) Swing.

Why?

Out of curiosity mostly... but also because in some cases, Swing is still used to make user interfaces in some
large, older projects. Switching to JavaFX or something more modern might not be possible in the short term, so why not
try and introduce some reactive programming?

Anyways... this may turn into something useful! Or not at all.

## Build and Test
Gradle is used to build and test the project.

### Build
To build, enter the following command in a terminal:
```$xslt
./gradlew build
```

### Test
To build and run tests, enter the following command in a terminal:
```$xslt
./gradlew check
``` 

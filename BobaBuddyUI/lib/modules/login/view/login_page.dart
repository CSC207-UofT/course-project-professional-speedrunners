import 'dart:ui';

import 'package:boba_buddy/core/repository/authentication_repository.dart';
import 'package:boba_buddy/modules/login/bloc/login_bloc.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:formz/formz.dart';

import 'create_account_page.dart';

class LoginPage extends StatelessWidget {
  const LoginPage({Key? key}) : super(key: key);

  static Page page() => const MaterialPage<void>(child: LoginPage());

  @override
  Widget build(BuildContext context) {
    return ScreenUtilInit(
      designSize: const Size(393, 830),
      builder: () => Scaffold(
          resizeToAvoidBottomInset: false,
          body: BlocProvider(
            create: (context) {
              return LoginBloc(
                authenticationRepository:
                    context.read<AuthenticationRepository>(),
              );
            },
            child: const LoginForm(),
          )),
    );
  }
}

class LoginForm extends StatelessWidget {
  const LoginForm({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Stack(children: [
        //Draws background image and sets stack parent
        Container(
          width: 400.w,
          height: 1000.h,
          decoration: const BoxDecoration(
            image: DecorationImage(
                image: AssetImage("assets/images/BubbleTeaBackground.png"),
                fit: BoxFit.cover),
          ),
        ),
        //Login page tile
        Padding(
          padding: EdgeInsets.only(top: 100.h, left: 50.w, right: 50.w),
          child: const Text(
            "Boba Buddy",
            style: TextStyle(
                fontFamily: "Josefin Sans",
                color: Colors.white,
                fontSize: 50,
                fontWeight: FontWeight.bold),
          ),
        ),
        //Email and Password Field
        Padding(
            padding: EdgeInsets.only(top: 300.h),
            child: BlocListener<LoginBloc, LoginState>(
              listener: (context, state) {
                if (state.status.isSubmissionFailure) {
                  _buildErrorToast("Authentication Failure");
                }
              },
              child: Center(
                child: Column(children: [
                  _UsernameInput(),
                  _PasswordInput(),
                  _LoginButton(),
                  Padding(
                    padding: EdgeInsets.only(top: 25.h),
                    child: TextButton(
                      onPressed: () {
                        Navigator.of(context).push(MaterialPageRoute(
                            builder: (context) => CreateAccountPage()));
                      },
                      style: ButtonStyle(
                        overlayColor: MaterialStateProperty.resolveWith<Color?>(
                            (Set<MaterialState> states) {
                          if (states.contains(MaterialState.pressed)) {
                            return Colors.white.withOpacity(0.2);
                          }
                          return null; // Defer to the widget's default.
                        }),
                      ),
                      child: const Text(
                        'Create New Account',
                        style: TextStyle(
                            decoration: TextDecoration.underline,
                            fontSize: 20,
                            fontFamily: "Josefin Sans",
                            fontWeight: FontWeight.bold,
                            color: Colors.white),
                      ),
                    ),
                  )
                ]),
              ),
            ))
      ]),
    );
  }
}

class _UsernameInput extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<LoginBloc, LoginState>(
      buildWhen: (previous, current) => previous.email != current.email,
      builder: (context, state) {
        return Padding(
            padding: EdgeInsets.only(left: 35.w, right: 35.w, top: 25.h),
            child: TextField(
              style: const TextStyle(color: Colors.white),
              key: const Key('loginForm_emailInput_textField'),
              onChanged: (username) =>
                  context.read<LoginBloc>().add(LoginUsernameChanged(username)),
              decoration: InputDecoration(
                  errorText: state.email.invalid ? 'invalid email' : null,
                  prefixIcon: const Icon(
                    Icons.email_outlined,
                    color: Colors.white,
                  ),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(10.0),
                    borderSide: const BorderSide(
                      width: 0,
                      style: BorderStyle.none,
                    ),
                  ),
                  filled: true,
                  hintStyle: const TextStyle(
                      fontFamily: "Josefin Sans",
                      fontSize: 20,
                      color: Colors.white,
                      fontWeight: FontWeight.bold),
                  hintText: "Email",
                  fillColor: Colors.grey.withOpacity(.4)),
            ));
      },
    );
  }
}

class _PasswordInput extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<LoginBloc, LoginState>(
      buildWhen: (previous, current) => previous.password != current.password,
      builder: (context, state) {
        return Padding(
          padding: EdgeInsets.only(left: 35.w, right: 35.w, top: 25.h),
          child: TextField(
            key: const Key('loginForm_passwordInput_textField'),
            onChanged: (password) =>
                context.read<LoginBloc>().add(LoginPasswordChanged(password)),
            obscureText: true,
            decoration: InputDecoration(
                errorText: state.password.invalid ? 'invalid password' : null,
                prefixIcon: const Icon(
                  Icons.lock_outlined,
                  color: Colors.white,
                ),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: const BorderSide(
                    width: 0,
                    style: BorderStyle.none,
                  ),
                ),
                filled: true,
                hintStyle: const TextStyle(
                    fontFamily: "Josefin Sans",
                    fontSize: 20,
                    color: Colors.white,
                    fontWeight: FontWeight.bold),
                hintText: "Password",
                fillColor: Colors.grey.withOpacity(.4)),
          ),
        );
      },
    );
  }
}

class _LoginButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Padding(
        padding: EdgeInsets.only(top: 120.h),
        child: BlocBuilder<LoginBloc, LoginState>(
          buildWhen: (previous, current) => previous.status != current.status,
          builder: (context, state) {
            return state.status.isSubmissionInProgress
                ? const CircularProgressIndicator()
                : ElevatedButton(
                    key: const Key('loginForm_continue_raisedButton'),
                    child: const Text(
                      'Login',
                      style: TextStyle(
                          fontFamily: "Josefin Sans",
                          fontWeight: FontWeight.bold,
                          fontSize: 20),
                    ),
                    style: ElevatedButton.styleFrom(
                      shape: const RoundedRectangleBorder(
                          borderRadius: BorderRadius.all(Radius.circular(8))),
                      minimumSize: Size(300.w, 64.h),
                      primary: const Color.fromRGBO(86, 99, 255, 1),
                    ),
                    onPressed: state.status.isValidated
                        ? () {
                            context
                                .read<LoginBloc>()
                                .add(const LoginSubmitted());
                          }
                        : null,
                  );
          },
        ));
  }
}

///Builder for TextFeilds
Widget _buildTextField(BuildContext context, String hintText, Icon icon,
    TextEditingController controller,
    {obscure = false}) {
  return Padding(
    padding: EdgeInsets.only(left: 35.w, right: 35.w, top: 25.h),
    child: TextFormField(
      obscureText: obscure,
      controller: controller,
      style: const TextStyle(color: Colors.white),
      decoration: InputDecoration(
          prefixIcon: icon,
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(10.0),
            borderSide: const BorderSide(
              width: 0,
              style: BorderStyle.none,
            ),
          ),
          filled: true,
          hintStyle: const TextStyle(
              fontFamily: "Josefin Sans",
              fontSize: 20,
              color: Colors.white,
              fontWeight: FontWeight.bold),
          hintText: hintText,
          fillColor: Colors.grey.withOpacity(.4)),
    ),
  );
}

_buildErrorToast(String message) {
  Fluttertoast.showToast(
    msg: message,
    toastLength: Toast.LENGTH_SHORT,
    gravity: ToastGravity.CENTER,
  );
}

bool _emailValid(email) {
  return RegExp(
          r"^[a-zA-Z0-9.a-zA-Z0-9.!#$%&'*+-/=?^_`{|}~]+@[a-zA-Z0-9]+\.[a-zA-Z]+")
      .hasMatch(email);
}

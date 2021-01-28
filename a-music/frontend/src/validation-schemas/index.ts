import * as Yup from 'yup';

const phoneRegExp: any = /^(\s*)?(\+)?([- _():=+]?\d[- _():=+]?){10,14}(\s*)?$/;
const nameRegexp = /^[а-яёА-ЯЁa-zA-Z]{1,20}$/gi;
const userRegexp = /^[^\.][a-zA-Zа-яёА-ЯЁ0-9\-!#$%&'*+\/=?^_`{|}~]{0,20}$/gi;
const emailRegexp = /^[a-z0-9\-!#$%&'*+\/=?^_`{|}~]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/gi;
const passwordRegexp = /^[\S]{0,}$/;

export const PhoneSchema = Yup.object().shape({
  phone: Yup.string().matches(phoneRegExp, 'Phone number is not valid').required('Required'),
});

export const UsernamePasswordSchema = Yup.object().shape({
  username: Yup.string().min(10, 'Input valid number').max(14, 'Input valid number').required('Required'),
  password: Yup.string()
    .min(8, 'Must be 8 characters or more')
    .max(32, 'Must be 32 characters or less')
    .required('Required'),
});

export const PasswordSchema = Yup.object({
  password: Yup.string()
    .required('Required')
    .max(32, 'Must be 32 characters or less')
    .min(8, 'Must be 8 characters or more')
    .matches(passwordRegexp, 'Must not contain whitespaces'),
  repeatPassword: Yup.string()
    .required('Required')
    .matches(passwordRegexp, 'Must not contain whitespaces')
    .oneOf([Yup.ref('password'), null], 'Passwords must match'),
});

export const PhoneSmsSchema = Yup.object({
  code: Yup.string().required('Required'),
});

export const RegistrationSchema = Yup.object({
  username: Yup.string()
    .matches(userRegexp, 'Invalid characters entered')
    .required('Required')
    .max(20, 'Must be 20 characters or less')
    .min(2, 'Must be 2 characters or more'),
  firstName: Yup.string()
    .matches(nameRegexp, 'Must contain only letters')
    .required('Required')
    .max(20, 'Must be 20 characters or less')
    .min(2, 'Must be 2 characters or more'),
  lastName: Yup.string()
    .matches(nameRegexp, 'Must contain only letters')
    .required('Required')
    .max(20, 'Must be 20 characters or less')
    .min(2, 'Must be 2 characters or more'),
  email: Yup.string()
    .matches(emailRegexp, 'Invalid email address')
    .required('Required')
    .max(30, 'Must be 30 characters or less')
    .min(5, 'Must be 5 characters or more'),
  password: Yup.string()
    .required('Required')
    .max(32, 'Must be 32 characters or less')
    .min(8, 'Must be 8 characters or more')
    .matches(passwordRegexp, 'Must not contain whitespaces'),
  repeatPassword: Yup.string()
    .required('Required')
    .matches(passwordRegexp, 'Must not contain whitespaces')
    .oneOf([Yup.ref('password'), null], 'Passwords must match'),
});

export const PasswordChangeSchema = Yup.object({
  oldPassword: Yup.string().required('Required'),
  password: Yup.string()
    .required('Required')
    .max(32, 'Must be 32 characters or less')
    .min(8, 'Must be 8 characters or more')
    .matches(passwordRegexp, 'Must not contain whitespaces'),
  repeatPassword: Yup.string()
    .required('Required')
    .oneOf([Yup.ref('password'), null], 'Passwords must match')
    .matches(passwordRegexp, 'Must not contain whitespaces'),
});

export const AddCardSchema = Yup.object().shape({
  number: Yup.string().required('Required').min(19, 'Wrong card number'),
  name: Yup.string()
    .required('Required')
    .matches(/[а-яА-ЯёЁa-zA-Z]/g, 'Must contain only letters'),
  expiry: Yup.string().required('Required').min(5, 'Wrong expiry'),
  cvc: Yup.string().required('Required').min(3, 'Wrong cvc number'),
});

export const ChangeUserSchema = Yup.object().shape({
  username: Yup.string()
    .required('Required')
    .max(16, 'Must be 16 characters or less')
    .min(3, 'Must be 3 characters or more'),
  firstName: Yup.string()
    .matches(/^[а-яА-ЯёЁa-zA-Z]+$/, 'Must contain only letters')
    .max(25, 'Must be 25 characters or less')
    .min(1, 'Must be 1 characters or more')
    .required('Required'),
  lastName: Yup.string()
    .matches(/^[а-яА-ЯёЁa-zA-Z]+$/, 'Must contain only letters')
    .max(25, 'Must be 25 characters or less')
    .min(1, 'Must be 1 characters or more')
    .required('Required'),
  phone: Yup.string().required('Required'),
  email: Yup.string().email('Invalid email address').required('Required'),
});

export const SendMusicFormSchema = Yup.object().shape({
  author: Yup.string().required('Required'),
  title: Yup.string().required('Required'),
  album: Yup.string().required('Required'),
  type: Yup.string().required('Required'),
});

export const SendPlayListFormSchema = Yup.object().shape({
  title: Yup.string().required('Required'),
  description: Yup.string().required('Required'),
});

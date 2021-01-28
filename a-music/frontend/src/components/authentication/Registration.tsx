import React, { useContext } from 'react';
import { Formik, FormikProps, ErrorMessage } from 'formik';
import { Button } from '@material-ui/core';
import { AuthContext } from '../../HOC/withAuthContext';
import { createUser } from '../../actions/user';
import { RegistrWrapper, StyledTextField, LinkBackNumber, BlockLinkBack } from './registration-styles';
import PasswordInput from './PasswordInput';
import { RegistrationSchema } from '../../validation-schemas';
import { IModalChild } from '../../ui/modal';
import { toast } from 'react-toastify';
import { ArrowBack } from './svg';

interface IFormikValues {
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  repeatPassword: string;
}

const Registration = ({ nextStep }: IModalChild) => {
  const { values, setValues } = useContext(AuthContext);
  const { username, firstName, lastName, email, password, repeatPassword, phone } = values;

  const InitialValues: IFormikValues = {
    username,
    firstName,
    lastName,
    email,
    password,
    repeatPassword,
  };

  const handleBack = () => {
    nextStep(false);
  };

  const handleSubmit = async (formValues: IFormikValues) => {
    try {
      await createUser({ ...values, ...formValues });
      setValues({ ...values, ...formValues });
      nextStep(true);
    } catch (e) {
      toast.error(e.message);
    }
  };

  return (
    <React.Fragment>
      <BlockLinkBack className={'link-back-block'}>
        <LinkBackNumber className={'link-back-number'} onClick={handleBack}>
          <ArrowBack />
          {phone}
        </LinkBackNumber>
      </BlockLinkBack>
      <Formik initialValues={InitialValues} onSubmit={handleSubmit} validationSchema={RegistrationSchema}>
        {({ values, handleChange, errors, touched }: FormikProps<IFormikValues>) => (
          <RegistrWrapper>
            <div className={'fields-container'}>
              <div className={'left-block-container'}>
                <StyledTextField
                  error={!!(touched.firstName && errors.firstName)}
                  className={errors.firstName ? 'text-field-error' : ''}
                  variant="outlined"
                  size="medium"
                  type="text"
                  value={values.firstName}
                  onChange={handleChange}
                  name="firstName"
                  placeholder="First Name"
                />
                <ErrorMessage name="firstName" component="span" className="error" />
                <StyledTextField
                  error={!!(touched.username && errors.username)}
                  className={errors.username ? 'text-field-error' : ''}
                  variant="outlined"
                  size="medium"
                  type="text"
                  value={values.username}
                  onChange={handleChange}
                  name="username"
                  placeholder="Username"
                />
                <ErrorMessage name="username" component="span" className="error" />
                <PasswordInput
                  id="password"
                  name="password"
                  placeholder="Password"
                  value={values.password}
                  className={'password-input'}
                  error={!!(touched.password && errors)}
                  labelWidth={70}
                  onChange={handleChange}
                />
                <ErrorMessage name="password" component="span" className="error" />
              </div>
              <div className={'right-block-container'}>
                <StyledTextField
                  error={!!(touched.lastName && errors.lastName)}
                  className={errors.lastName ? 'text-field-error' : ''}
                  variant="outlined"
                  size="medium"
                  type="text"
                  value={values.lastName}
                  onChange={handleChange}
                  name="lastName"
                  placeholder="Last Name"
                />
                <ErrorMessage name="lastName" component="span" className="error" />
                <StyledTextField
                  error={!!(touched.email && errors.email)}
                  className={errors.email ? 'text-field-error' : ''}
                  variant="outlined"
                  size="medium"
                  type="text"
                  value={values.email}
                  onChange={handleChange}
                  name="email"
                  placeholder="Email"
                />
                <ErrorMessage name="email" component="span" className="error" />
                <PasswordInput
                  id="repeatPassword"
                  name="repeatPassword"
                  placeholder="Confirm password"
                  value={values.repeatPassword}
                  className={'password-input'}
                  error={!!(touched.repeatPassword && errors)}
                  labelWidth={130}
                  onChange={handleChange}
                />
                <ErrorMessage name="repeatPassword" component="span" className="error" />
              </div>
            </div>
            <Button size="medium" type="submit" className="registration-submit" fullWidth>
              Sign Up
            </Button>
          </RegistrWrapper>
        )}
      </Formik>
    </React.Fragment>
  );
};

export default Registration;

import React, { useContext, useState } from 'react';
import { Formik, FormikProps, Field, FieldProps } from 'formik';
import { useHistory } from 'react-router-dom';
import { HOME_PATH } from '../../router';
import { WrappedForm, StyledPhoneInput } from './form-styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import InputAdornment from '@material-ui/core/InputAdornment';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';
import 'react-phone-input-2/lib/material.css';
import { UsernamePasswordSchema } from '../../validation-schemas';
import { ThemeContext } from '../../App';
import { AuthContext, IFormValues } from '../../HOC/withAuthContext';
import { connect } from '../../helpers/connect';
import { bindActionCreators } from 'redux';
import { signIn } from '../../actions/user';
import { REGISTRATION_SCHEME, CHANGE_PASSWORD_SCHEME } from '../../HOC/withSchemaContext';
import { IModalChild } from '../../ui/modal';
import { toast } from 'react-toastify';

interface ISingIn extends IModalChild {
  signIn: (values: IFormValues) => void;
}

const Authenticate = ({ signIn, nextStep }: ISingIn) => {
  const { values } = useContext(AuthContext);
  const { handleChangeWrapper } = useContext(ThemeContext);
  const handlePasswordRecoveryForm = () => {
    nextStep(true, CHANGE_PASSWORD_SCHEME);
  };
  const handleSignUpForm = () => {
    nextStep(true, REGISTRATION_SCHEME);
  };
  const { push } = useHistory();

  const [showPassword, setShowPassword] = useState(false);

  const handleClickShowPassword = () => {
    setShowPassword(!showPassword);
  };

  const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
  };

  const handleSubmit = async (formValues: IFormValues) => {
    try {
      await signIn(formValues);
      push(HOME_PATH);
      handleChangeWrapper(localStorage.getItem('JWT_TOKEN_KEY'));
    } catch (e) {
      toast.error(e.message);
    }
  };

  return (
    <>
      <Formik id="formik" initialValues={values} onSubmit={handleSubmit} validationSchema={UsernamePasswordSchema}>
        {({ errors }: FormikProps<IFormValues>) => (
          <WrappedForm data-testid="authenticate-form">
            <div className={'container-for-inputs'}>
              <div>
                <div>
                  <Field name="username">
                    {({ field, form }: FieldProps<string>) => {
                      const handleChange = (value: string) => form.setFieldValue('username', `+${value}`);
                      return (
                        <StyledPhoneInput
                          inputClass={errors.phone ? 'phone-input phone-error' : 'phone-input'}
                          {...field}
                          value={values.phone}
                          onChange={handleChange}
                          country={'ua'}
                          placeholder="Enter your username"
                        />
                      );
                    }}
                  </Field>
                  {errors.username && <div className="phone-error-text">{errors.username}</div>}
                </div>
                <div className={'container-for-remember'}>
                  <Field name="remember">
                    {({ field, form }: FieldProps) => {
                      const handleChange = (value: boolean) => form.setFieldValue('remember', value);
                      return (
                        <input
                          type="checkbox"
                          className="custom-checkbox"
                          id="remember"
                          {...field}
                          onChange={(e) => handleChange(e.target.checked)}
                        />
                      );
                    }}
                  </Field>
                  <label className={'label-checkbox'} htmlFor="remember">
                    Remember me
                  </label>
                </div>
              </div>
              <div className={errors.password ? 'password pass-error' : 'password'}>
                <Field name="password">
                  {({ field }: FieldProps<string>) => (
                    <TextField
                      type={showPassword ? 'text' : 'password'}
                      placeholder="Password"
                      error={!!errors.password}
                      helperText={errors.password}
                      {...field}
                      fullWidth
                      InputProps={{
                        endAdornment: (
                          <InputAdornment position="end">
                            <IconButton
                              aria-label="toggle password visibility"
                              className="toggle-pass-visibility"
                              onClick={handleClickShowPassword}
                              onMouseDown={handleMouseDownPassword}
                              edge="end"
                              data-testid="showPassword-btn"
                            >
                              {showPassword ? <Visibility /> : <VisibilityOff />}
                            </IconButton>
                          </InputAdornment>
                        ),
                      }}
                    />
                  )}
                </Field>
                <Button className="password-recover" size="small" onClick={handlePasswordRecoveryForm}>
                  Forgot password?
                </Button>
              </div>
            </div>
            <div className={'container-for-buttons'}>
              <Button
                id="authBtn"
                className="auth-button"
                fullWidth
                type="submit"
                size="large"
                data-testid="submit-btn"
              >
                Login
              </Button>
              <div className={'container-for-sign-up'}>
                <span>Don’t have an account?</span>
                <a onClick={handleSignUpForm}>Sing Up</a>
              </div>
            </div>
          </WrappedForm>
        )}
      </Formik>
    </>
  );
};

export default (connect(null, (dispatch) => bindActionCreators({ signIn }, dispatch)) as any)(Authenticate);

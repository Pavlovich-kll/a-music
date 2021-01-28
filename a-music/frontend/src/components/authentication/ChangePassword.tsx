import React, { useContext, useState } from 'react';
import { Formik, FormikProps } from 'formik';
import { Button } from '@material-ui/core';
import { resetPassword } from '../../actions/user';
import { WrappedForm } from './form-styles';
import TextField from '@material-ui/core/TextField';
import InputAdornment from '@material-ui/core/InputAdornment';
import IconButton from '@material-ui/core/IconButton';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';
import { PasswordSchema } from '../../validation-schemas';
import { toast } from 'react-toastify';
import { AuthContext } from '../../HOC/withAuthContext';
import { IModalChild } from '../../ui/modal';

interface IFormikValues {
  password: string;
  repeatPassword: string;
}

const ChangePassword = ({ nextStep }: IModalChild) => {
  const [showPassword, setShowPassword]: any = useState<{ [k: string]: boolean }>({
    password: false,
    repeatPassword: false,
  });
  const { values } = useContext(AuthContext);
  const handleShowPass = (type: string) => setShowPassword({ ...showPassword, [type]: !showPassword[type] });

  const handleSubmit = async (formValues: IFormikValues) => {
    try {
      await resetPassword(formValues.password, formValues.repeatPassword);
      nextStep(true);
    } catch (e) {
      toast.error(e.message);
    }
  };

  return (
    <>
      <Formik initialValues={values} onSubmit={handleSubmit} validationSchema={PasswordSchema}>
        {({ values, handleChange, errors, touched }: FormikProps<IFormikValues>) => (
          <WrappedForm data-testid="authenticate-form">
            <div className={'container-for-recovery-pass'}>
              <span className="span-pass-recovery">Please enter your new password:</span>
              <div className="password pass-recovery">
                <TextField
                  name="password"
                  type={showPassword.password ? 'text' : 'password'}
                  value={values.password}
                  onChange={handleChange}
                  placeholder="Password"
                  fullWidth
                  error={!!(touched.password && errors.password)}
                  helperText={touched.password && errors.password}
                  InputProps={{
                    endAdornment: (
                      <InputAdornment position="end">
                        <IconButton
                          aria-label="toggle password visibility"
                          className="toggle-pass-visibility"
                          onClick={handleShowPass.bind(null, 'password')}
                          edge="end"
                          data-testid="showPassword-btn"
                        >
                          {showPassword.password ? <Visibility /> : <VisibilityOff />}
                        </IconButton>
                      </InputAdornment>
                    ),
                  }}
                />
              </div>
              <div className="password pass-recovery">
                <TextField
                  name="repeatPassword"
                  type={showPassword.repeatPassword ? 'text' : 'password'}
                  value={values.repeatPassword}
                  onChange={handleChange}
                  placeholder="Confirm password"
                  fullWidth
                  error={!!(touched.repeatPassword && errors.repeatPassword)}
                  helperText={touched.repeatPassword && errors.repeatPassword}
                  InputProps={{
                    endAdornment: (
                      <InputAdornment position="end">
                        <IconButton
                          aria-label="toggle password visibility"
                          className="toggle-pass-visibility"
                          onClick={handleShowPass.bind(null, 'repeatPassword')}
                          edge="end"
                          data-testid="showPassword-btn"
                        >
                          {showPassword.repeatPassword ? <Visibility /> : <VisibilityOff />}
                        </IconButton>
                      </InputAdornment>
                    ),
                  }}
                />
              </div>
            </div>
            <div className={'container-for-buttons container-for-pass-recovery'}>
              <Button
                id="authBtn"
                className="auth-button"
                fullWidth
                type="submit"
                size="large"
                data-testid="submit-btn"
              >
                Confirm
              </Button>
            </div>
          </WrappedForm>
        )}
      </Formik>
    </>
  );
};

export default ChangePassword;

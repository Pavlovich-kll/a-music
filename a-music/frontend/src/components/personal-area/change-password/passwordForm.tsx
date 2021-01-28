import React, { useState } from 'react';
import { Formik, FormikProps, ErrorMessage } from 'formik';
import { Button } from '@material-ui/core';
import { FormWrapper } from './styles';
import PasswordInput from '../../authentication/PasswordInput';
import SnackBar from '../../SnackBar/SnackBar';
import { PasswordChangeSchema } from '../../../validation-schemas';
import { updatePassword } from '../../../actions/user';

interface IFormikValues {
  oldPassword: string;
  password: string;
  repeatPassword: string;
}

const InitialValues: IFormikValues = {
  oldPassword: '',
  password: '',
  repeatPassword: '',
};

interface IPasswordFormProps {
  onClose: () => void;
}

const PasswordForm = ({ onClose }: IPasswordFormProps) => {
  const [open, setOpen] = useState(false);
  const [error, setError] = useState('');
  const handleClose = (event: React.SyntheticEvent | React.MouseEvent, reason?: string) => {
    if (reason !== 'clickaway') {
      setOpen(false);
    }
  };

  const handleSubmit = async (values: IFormikValues) => {
    try {
      await updatePassword(values.password, values.repeatPassword, values.oldPassword);
      onClose();
    } catch (error) {
      setError(error.toString());
      setOpen(true);
    }
  };

  return (
    <>
      <SnackBar isOpen={open} error={error} handleClose={handleClose} />
      <Formik initialValues={InitialValues} onSubmit={handleSubmit} validationSchema={PasswordChangeSchema}>
        {({ values, handleChange, errors, touched }: FormikProps<IFormikValues>) => (
          <FormWrapper>
            <PasswordInput
              id="oldPassword"
              name="oldPassword"
              placeholder="Type old password..."
              value={values.oldPassword}
              error={!!(touched.oldPassword && errors)}
              label="Old Password"
              labelWidth={70}
              onChange={handleChange}
            />
            <ErrorMessage name="oldPassword" component="span" className="error" />
            <PasswordInput
              id="password"
              name="password"
              placeholder="Type password..."
              value={values.password}
              error={!!(touched.password && errors)}
              label="Password"
              labelWidth={70}
              onChange={handleChange}
            />
            <ErrorMessage name="password" component="span" className="error" />
            <PasswordInput
              id="repeatPassword"
              name="repeatPassword"
              placeholder="Repeat password..."
              value={values.repeatPassword}
              error={!!(touched.repeatPassword && errors)}
              label="Repeat password"
              labelWidth={130}
              onChange={handleChange}
            />
            <ErrorMessage name="repeatPassword" component="span" className="error" />
            <Button variant="outlined" size="medium" type="submit" color="primary" fullWidth>
              Submit
            </Button>
          </FormWrapper>
        )}
      </Formik>
    </>
  );
};

export default PasswordForm;

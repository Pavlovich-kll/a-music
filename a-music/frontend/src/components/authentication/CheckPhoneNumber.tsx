import React, { useContext } from 'react';
import { Field, Formik, FormikProps } from 'formik';
import { AuthContext } from '../../HOC/withAuthContext';
import { WrappedForm } from './form-styles';
import { Button } from '@material-ui/core';
import { PhoneSchema } from '../../validation-schemas';
import PhoneInput from 'react-phone-input-2';
import 'react-phone-input-2/lib/material.css';
import { toast } from 'react-toastify';
import { IModalChild } from '../../ui/modal';

interface IFormikValues {
  phone: string;
}

interface ICheckPhoneNumber extends IModalChild {
  checkPhoneToProceed(phone: string): void;
}

const CheckPhoneNumber = ({ nextStep, checkPhoneToProceed }: ICheckPhoneNumber) => {
  const { values, setValues } = useContext(AuthContext);

  const handleSubmit = async (formValues: IFormikValues) => {
    try {
      await checkPhoneToProceed(formValues.phone);
      nextStep(true);
    } catch (e) {
      toast.error(e.message);
    } finally {
      setValues({ ...values, ...formValues });
    }
  };

  return (
    <Formik initialValues={values} onSubmit={handleSubmit} validationSchema={PhoneSchema}>
      {({ errors }: FormikProps<IFormikValues>) => (
        <WrappedForm>
          <Field name="phone">
            {({ field, form }: { field: any; form: FormikProps<IFormikValues> }) => {
              const handleChange = (value: string) => form.setFieldValue('phone', `+${value}`);
              return (
                <PhoneInput
                  inputClass={errors.phone ? 'phone-input phone-error' : 'phone-input'}
                  onChange={handleChange}
                  country={'ua'}
                  value={field.value}
                  placeholder="Enter your phone"
                />
              );
            }}
          </Field>
          {errors.phone && <div className="phone-error-text">{errors.phone}</div>}
          <Button fullWidth type="submit" className="phone-check-submit" data-testid="submit-btn">
            Continue
          </Button>
        </WrappedForm>
      )}
    </Formik>
  );
};

export default CheckPhoneNumber;

import React, { useContext, useState } from 'react';
import { Formik, FormikProps, ErrorMessage } from 'formik';
import { Button } from '@material-ui/core';
import { BlockLinkBack, LinkBackNumber, RegistrWrapper, StyledTextCodeField } from './registration-styles';
import { Statistic } from 'antd';
import { verifyPhone } from '../../actions/user';
import { AuthContext } from '../../HOC/withAuthContext';
import { PhoneSmsSchema } from '../../validation-schemas';
import { toast } from 'react-toastify';
import { IModalChild } from '../../ui/modal';
import { ArrowBack } from './svg';

interface IFormikValues {
  code: string;
}

interface ICheckPhoneNumber extends IModalChild {
  checkPhoneToProceed(phone: string): void;
}

const PhoneVerificationSignUp = ({ nextStep, checkPhoneToProceed }: ICheckPhoneNumber) => {
  const { values, setValues } = useContext(AuthContext);
  const { Countdown } = Statistic;
  const deadline = Date.now() + 1000 * 61;
  const [showTime, setShowTime] = useState(true);

  const onFinish = () => {
    setShowTime(false);
  };

  const handleBack = () => {
    nextStep(false);
  };

  const handleSubmit = async (data: IFormikValues) => {
    try {
      await verifyPhone(values.phone, +data.code);
      setValues({ ...values, ...data });
      nextStep(true);
    } catch (e) {
      toast.error(e);
    }
  };

  const resendCode = async () => {
    try {
      await checkPhoneToProceed(values.phone);
    } catch (e) {
      toast.error(e.message);
    } finally {
      setShowTime(true);
    }
  };

  return (
    <>
      <BlockLinkBack className={'link-back-block'}>
        <LinkBackNumber className={'link-back-number'} onClick={handleBack}>
          <ArrowBack />
          {values.phone}
        </LinkBackNumber>
      </BlockLinkBack>
      <Formik initialValues={{ code: '' }} onSubmit={handleSubmit} validationSchema={PhoneSmsSchema}>
        {({ values, handleChange, errors, touched }: FormikProps<IFormikValues>) => {
          return (
            <RegistrWrapper>
              <div className={'fields-code-container'}>
                <span className="span-title">Please enter the verification code sent to your phone number</span>
                <StyledTextCodeField
                  error={!!(touched.code && errors.code)}
                  className={errors.code ? 'text-field-error' : ''}
                  variant="outlined"
                  size="medium"
                  type="text"
                  value={values.code}
                  onChange={handleChange}
                  name="code"
                  placeholder="Verification code"
                />
                <ErrorMessage name="code" component="span" className="error" />
              </div>
              <Button size="medium" type="submit" className="registration-submit verification-submit" fullWidth>
                Verify
              </Button>
              <div className="container-for-text">
                <span className="text-small-span">Didn’t recieve the code?</span>
                {showTime ? (
                  <span className="text-small-span">
                    Wait for
                    <Countdown value={deadline} onFinish={onFinish} />
                    and
                    <span className="text-small-span text-gray-span">Request a new verification code</span>
                  </span>
                ) : (
                  <span className="text-small-span text-gray-span text-button-span" onClick={resendCode}>
                    Request a new verification code
                  </span>
                )}
              </div>
            </RegistrWrapper>
          );
        }}
      </Formik>
    </>
  );
};

export default PhoneVerificationSignUp;

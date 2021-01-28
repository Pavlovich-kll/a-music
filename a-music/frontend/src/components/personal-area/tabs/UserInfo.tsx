import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Field, Formik, Form, FormikProps } from 'formik';
import { fieldsList } from '../constant';
import PhoneInput from 'react-phone-input-2';
import { TextField, IconButton } from '@material-ui/core';
import CheckIcon from '@material-ui/icons/Check';
import CloseIcon from '@material-ui/icons/Close';
import CreateIcon from '@material-ui/icons/Create';
import Tooltip from '@material-ui/core/Tooltip';
import { updateUser } from '../../../actions/user';
import ChangePassword from '../change-password';
import { ChangeUserSchema } from '../../../validation-schemas';

interface IRootState {
  user: any;
  current: object;
}

interface IFormikValues {
  username: any;
  firstName: any;
  lastName: any;
  phone: any;
  email: any;
}

const UserInfo = () => {
  const dispatch = useDispatch();
  const currentUser = useSelector((state: IRootState) => state.user.current);
  const [change, setChange] = useState<string>('');
  const InitialValues: IFormikValues = {
    username: currentUser.username,
    firstName: currentUser.firstName,
    lastName: currentUser.lastName,
    phone: currentUser.phone,
    email: currentUser.email,
  };

  const handleSubmit = async (values: IFormikValues) => {
    await dispatch(updateUser(currentUser.id, values));
    setChange('');
  };

  const handleSubmitClose = () => {
    setChange('');
  };

  const handleSubmitChange = (value: string) => () => {
    setChange(value);
  };

  return (
    <>
      <Formik
        id="formikProfile"
        onSubmit={handleSubmit}
        initialValues={InitialValues}
        validationSchema={ChangeUserSchema}
        onReset={handleSubmitClose}
        validateOnBlur
      >
        {({ errors, values, handleChange, onSubmit, setFieldValue }: any) => (
          <Form>
            {fieldsList.map((item) => {
              const data = item.data as string;
              if (item.title === 'Phone') {
                return (
                  <div key={item.title} className="flexWrapper">
                    <div className="itemTitle">{item.title}:</div>
                    {change === item.title ? (
                      <div className="flex">
                        <Field name="phone" className="phoneInput">
                          {({ form }: { form: FormikProps<IFormikValues> }) => {
                            const handleChange = (value: string) => form.setFieldValue('phone', `+${value}`);
                            return (
                              <PhoneInput
                                inputProps={{
                                  autoFocus: true,
                                }}
                                onChange={handleChange}
                                value={currentUser[data]}
                              />
                            );
                          }}
                        </Field>
                        <Tooltip title="Confirm">
                          <IconButton className="submitButton" type="submit" aria-label="yes">
                            <CheckIcon />
                          </IconButton>
                        </Tooltip>
                        <Tooltip title="Cancel">
                          <IconButton
                            className="closeButton"
                            onClick={() => {
                              setFieldValue('phone', currentUser[data]);
                              handleSubmitClose();
                            }}
                            aria-label="close"
                          >
                            <CloseIcon />
                          </IconButton>
                        </Tooltip>
                      </div>
                    ) : (
                      <div className="flex">
                        <div className="userData">{currentUser[data]}</div>
                        <div onClick={handleSubmitChange(item.title)} className="wrapperButton" aria-label="change">
                          <Tooltip title={`Change ${item.title}`}>
                            <IconButton>
                              <CreateIcon />
                            </IconButton>
                          </Tooltip>
                        </div>
                      </div>
                    )}
                  </div>
                );
              }
              return (
                <div key={item.title} className="flexWrapper">
                  <div className="itemTitle">{item.title}:</div>
                  {change === item.title ? (
                    <div className="flex">
                      <TextField
                        error={!!errors[item.data]}
                        helperText={errors[item.data]}
                        name={item.data}
                        variant="outlined"
                        onChange={handleChange}
                        size="small"
                        value={values[data]}
                        autoFocus={true}
                      />
                      <div>
                        <Tooltip title="Confirm">
                          <IconButton className="submitButton" onClick={onSubmit} type="submit" aria-label="yes">
                            <CheckIcon />
                          </IconButton>
                        </Tooltip>
                        <Tooltip title="Cancel">
                          <IconButton
                            className="closeButton"
                            onClick={() => {
                              setFieldValue(`${item.data}`, currentUser[data]);
                              handleSubmitClose();
                            }}
                            aria-label="close"
                          >
                            <CloseIcon />
                          </IconButton>
                        </Tooltip>
                      </div>
                    </div>
                  ) : (
                    <div className="flex">
                      <div>{currentUser[data]}</div>
                      <Tooltip title={`Change ${item.title}`}>
                        <IconButton
                          className="changeButton"
                          onClick={handleSubmitChange(item.title)}
                          aria-label="change"
                        >
                          <CreateIcon />
                        </IconButton>
                      </Tooltip>
                    </div>
                  )}
                </div>
              );
            })}
          </Form>
        )}
      </Formik>
      <ChangePassword />
    </>
  );
};

export default UserInfo;

import React from 'react';
import { Formik, Form, FormikProps } from 'formik';
import CardForm from '../styles';
import { AddCardSchema } from '../../../../validation-schemas';
import creditCardType from 'credit-card-type';
import { toast } from 'react-toastify';
import TextField from '@material-ui/core/TextField';
import { NumberMask } from './InputMasks';
import { ExpiryMask } from './InputMasks';
import { CvcMask } from './InputMasks';
import CardButton from '../../tabs/PaymentInfo/CardButton';

interface IOnClose {
  onClose: () => any;
}

interface IFormikValues {
  cvc: string;
  expiry: string;
  name: string;
  number: string;
}

export const AddCard = ({ onClose }: IOnClose) => {
  const card = JSON.parse(localStorage.getItem('card'));
  const initialValues: IFormikValues = {
    cvc: card ? card.cvc : '',
    expiry: card ? card.expiry : '',
    name: card ? card.name : '',
    number: card ? card.number : '',
  };

  const handleSubmit = (values: IFormikValues) => {
    const card = creditCardType(values.number)[0].type;
    if (!card) {
      toast.error('This card is not supported in our system');
    }
    localStorage.setItem('card', JSON.stringify(values));
    onClose();
  };

  return (
    <CardForm>
      <Formik initialValues={initialValues} onSubmit={handleSubmit} validationSchema={AddCardSchema}>
        {({ errors, values, handleChange, touched }: FormikProps<IFormikValues>) => (
          <Form>
            <div className="inputWrapper">
              <TextField
                name="number"
                variant="outlined"
                label="Card number"
                error={!!(touched.number && errors.number)}
                helperText={touched.number && errors.number}
                fullWidth
                InputProps={{
                  inputComponent: NumberMask as any,
                  value: values.number,
                  onChange: handleChange,
                }}
              />
            </div>
            <div className="inputWrapper">
              <TextField
                name="name"
                variant="outlined"
                label="Name Surname"
                error={!!(touched.name && errors.name)}
                helperText={touched.name && errors.name}
                value={values.name}
                onChange={handleChange}
                fullWidth
              />
            </div>
            <div className="inputWrapper">
              <div className="flex">
                <TextField
                  name="expiry"
                  variant="outlined"
                  label="Expiry"
                  error={!!(touched.expiry && errors.expiry)}
                  helperText={touched.expiry && errors.expiry}
                  InputProps={{
                    inputComponent: ExpiryMask as any,
                    value: values.expiry,
                    onChange: handleChange,
                  }}
                />
                <TextField
                  name="cvc"
                  variant="outlined"
                  label="CVC"
                  error={!!(touched.cvc && errors.cvc)}
                  helperText={touched.cvc && errors.cvc}
                  onChange={handleChange}
                  InputProps={{
                    inputComponent: CvcMask as any,
                    value: values.cvc,
                    onChange: handleChange,
                  }}
                />
              </div>
            </div>
            <CardButton card={card} />
          </Form>
        )}
      </Formik>
    </CardForm>
  );
};

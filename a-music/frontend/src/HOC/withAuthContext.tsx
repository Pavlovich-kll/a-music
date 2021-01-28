import React, { createContext, FC, useState } from 'react';

export interface IFormValues {
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  repeatPassword: string;
  phone: string;
  remember: boolean;
}

const INITIAL_VALUES = {
  phone: '',
  username: '',
  firstName: '',
  lastName: '',
  email: '',
  password: '',
  repeatPassword: '',
  remember: false,
};

export const AuthContext = createContext<{ values: IFormValues; setValues: any }>(null);
AuthContext.displayName = 'AuthContext';

export const withAuthContext = (Component: FC) => {
  function WithAuthContext(props: any) {
    const [values, setValues] = useState<IFormValues>(INITIAL_VALUES);

    return (
      <AuthContext.Provider value={{ values, setValues }}>
        <Component {...props} />
      </AuthContext.Provider>
    );
  }
  return WithAuthContext;
};

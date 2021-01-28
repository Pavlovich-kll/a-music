import React, { createContext, FC, useReducer, useState } from 'react';
import CheckPhoneNumber from '../components/authentication/CheckPhoneNumber';
import PhoneVerificationSignUp from '../components/authentication/CheckPhoneBySMS';
import Registration from '../components/authentication/Registration';
import Congratulations from '../components/authentication/Congratulations';
import Authenticate from '../components/authentication/Authenticate';
import ChangePassword from '../components/authentication/ChangePassword';
import withCheckNumberRegistration from '../components/authentication/withCheckNumberRegistration';
import withCheckNumberRecovery from '../components/authentication/withCheckNumberRecovery';

export interface IAuthSchemeContext {
  title: string;
  component: string;
  counter: number;
  schemaName: string;
}

interface IAction {
  type: string;
  payload?: boolean;
}

export const INITIAL_STATE = {
  title: '',
  component: '',
  counter: 0,
  schemaName: '',
};

export const AuthSchemeContext = createContext(null);
AuthSchemeContext.displayName = 'AuthSchemeContext';

const PHONE_REG_CHECK_STEP = { title: 'Sign Up', component: withCheckNumberRegistration(CheckPhoneNumber) };
const PHONE_RECOVERY_CHECK_STEP = { title: 'Recover Password', component: withCheckNumberRecovery(CheckPhoneNumber) };
const PHONE_VERIFICATION_STEP = {
  title: 'Phone Verification',
  component: withCheckNumberRecovery(PhoneVerificationSignUp),
};
const REGISTRATION_STEP = { title: 'Registration', component: Registration };
const CONGRATULATIONS_STEP = { title: 'Congratulations', component: Congratulations };
const AUTHENTICATE_STEP = { title: 'Log In', component: Authenticate };
const NEW_PASSWORD_STEP = { title: 'Set New Password', component: ChangePassword };

export const AUTH_SCHEME = 'USE_AUTHENTICATE_SCHEME';
export const REGISTRATION_SCHEME = 'USE_REGISTRATION_SCHEME';
export const CHANGE_PASSWORD_SCHEME = 'USE_CHANGE_PASSWORD_SCHEME';

const registrationScheme = [
  PHONE_REG_CHECK_STEP,
  PHONE_VERIFICATION_STEP,
  REGISTRATION_STEP,
  CONGRATULATIONS_STEP,
  AUTHENTICATE_STEP,
];

const changePasswordScheme = [PHONE_RECOVERY_CHECK_STEP, PHONE_VERIFICATION_STEP, NEW_PASSWORD_STEP, AUTHENTICATE_STEP];

const authenticateScheme = [AUTHENTICATE_STEP];

const schemes: any = {
  [REGISTRATION_SCHEME]: registrationScheme,
  [CHANGE_PASSWORD_SCHEME]: changePasswordScheme,
  [AUTH_SCHEME]: authenticateScheme,
};

function transformData(state: IAuthSchemeContext, type: string, payload: boolean = true) {
  if (!type) return { ...INITIAL_STATE };
  // Step back
  if (!payload && type === state.schemaName) {
    if (!schemes[type][state.counter - 1]) return { ...state };

    const counter = --state.counter;
    return {
      ...state,
      ...schemes[type][counter],
      counter: counter,
    };
  }
  // End Step back
  // Step forward
  if (type === state.schemaName) {
    if (!schemes[type][state.counter + 1]) return { ...state, ...schemes[type][0], counter: 0 };

    const counter = ++state.counter;

    return {
      ...state,
      ...schemes[type][counter],
      counter: counter,
    };
  }
  // End Step forward
  return {
    ...schemes[type][0],
    counter: 0,
    schemaName: type,
  };
}

export const reducer = (state: IAuthSchemeContext = INITIAL_STATE, { type, payload }: IAction) => {
  if (!!schemes[type]) return transformData(state, type, payload);
  return transformData(state, '');
};

const withSchemaContext = (Component: FC) => {
  function WithSchemaContext(props: any) {
    const [state, dispatch] = useReducer(reducer, INITIAL_STATE);
    const [visible, setVisible] = useState<boolean>(false);

    const handleUpdate = (type: string) => () => {
      if (!!type) {
        dispatch({ type });
        setVisible(true);
        return;
      }
      dispatch({ type: null });
      setVisible(!!type);
    };

    return (
      <AuthSchemeContext.Provider value={{ state, dispatch, visible, handleUpdate }}>
        <Component {...props} />
      </AuthSchemeContext.Provider>
    );
  }
  return WithSchemaContext;
};

export default withSchemaContext;

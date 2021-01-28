import React from 'react';
import { checkPhone } from '../../actions/user';
import { AuthSchemeContext, AUTH_SCHEME } from '../../HOC/withSchemaContext';

const withCheckNumberRegistration = (Component: any) => (props: any) => {
  const { handleUpdate } = React.useContext(AuthSchemeContext);
  const checkValue = async (number: string) => {
    const { exists } = await checkPhone(number);
    if (exists) {
      handleUpdate(AUTH_SCHEME)();
      throw new Error('Number is already registered!');
    }
  };

  return <Component {...props} checkPhoneToProceed={checkValue} />;
};

export default withCheckNumberRegistration;

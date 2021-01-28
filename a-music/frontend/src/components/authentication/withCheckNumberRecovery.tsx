import React from 'react';
import { checkPhone } from '../../actions/user';

const withCheckNumberRecovery = (Component: any) => (props: any) => {
  const checkValue = async (number: string) => {
    const { exists } = await checkPhone(number);
    if (!exists) throw new Error("Number isn't exist");
  };

  return <Component {...props} checkPhoneToProceed={checkValue} />;
};

export default withCheckNumberRecovery;

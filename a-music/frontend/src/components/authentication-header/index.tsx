import React, { useContext } from 'react';
import Modal from '../../ui/modal';
import ButtonsWrapper, { LoginButton } from '../player/styles';
import { AuthSchemeContext } from '../../HOC/withSchemaContext';
import { AUTH_SCHEME, REGISTRATION_SCHEME } from '../../HOC/withSchemaContext';

const AuthenticationHeader = () => {
  const { state, dispatch, handleUpdate, visible } = useContext(AuthSchemeContext);
  const Component = state && state.component ? state.component : () => <></>;

  return (
    <>
      <ButtonsWrapper>
        <LoginButton className={'singInButton'} onClick={handleUpdate(AUTH_SCHEME)}>
          Sign in
        </LoginButton>
        <LoginButton className={'singUpButton'} onClick={handleUpdate(REGISTRATION_SCHEME)}>
          Sign up
        </LoginButton>
      </ButtonsWrapper>
      <Modal title={state ? state.title : ''} size="authSize" show={visible} onClose={handleUpdate(null)}>
        {!!Component && (
          <Component
            nextStep={(forward: boolean, type: string = state.schemaName) => dispatch({ payload: forward, type })}
          />
        )}
      </Modal>
    </>
  );
};

export default AuthenticationHeader;

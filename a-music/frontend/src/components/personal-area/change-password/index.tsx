import React, { useMemo, useState } from 'react';
import PasswordForm from './passwordForm';
import { Button } from '@material-ui/core';
import Modal from '../../../ui/modal';
import { normalize } from '../../../helpers/string.utils';
import { isNil } from 'ramda';

const PASSWORD_COMPONENTS = {
  PasswordForm: PasswordForm,
};

const ChangePassword = () => {
  const [visible, setVisible] = useState<boolean>(false);
  const [component, setComponent] = useState<keyof typeof PASSWORD_COMPONENTS | null>(null);
  const ModalComponent = useMemo(() => PASSWORD_COMPONENTS[component], [component]);

  const handleCloseModal = () => {
    setVisible(false);
    setComponent(null);
  };

  const handlePasswordForm = () => {
    setVisible(true);
    setComponent('PasswordForm');
  };
  return (
    <>
      <span className="open-modal">
        <Button size="medium" onClick={handlePasswordForm} className="MuiButton-colored">
          Change password
        </Button>
      </span>

      {!!component && (
        <Modal title={normalize(component)} size="medium" show={visible} onClose={handleCloseModal}>
          {!isNil(component) && <ModalComponent onClose={handleCloseModal} />}
        </Modal>
      )}
    </>
  );
};

export default ChangePassword;

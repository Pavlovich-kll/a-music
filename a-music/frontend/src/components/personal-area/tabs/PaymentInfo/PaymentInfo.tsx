import React, { useMemo, useState } from 'react';
import { Card } from './Card';
import { Button } from '@material-ui/core';
import Modal from '../../../../ui/modal';
import { normalize } from '../../../../helpers/string.utils';
import { isNil } from 'ramda';
import { AddCard } from '../../modal-components/AddCard/AddCard';

const PROFILE_COMPONENTS = {
  AddCard: AddCard,
};

const PaymentInfo = () => {
  const card = JSON.parse(localStorage.getItem('card'));
  const [visible, setVisible] = useState<boolean>(false);
  const [component, setComponent] = useState<keyof typeof PROFILE_COMPONENTS | null>(null);
  const ModalComponent = useMemo(() => PROFILE_COMPONENTS[component], [component]);

  const handleCloseModal = () => {
    setVisible(false);
    setComponent(null);
  };

  const handleAddCard = () => {
    setVisible(true);
    setComponent('AddCard');
  };
  return (
    <>
      <Card card={card} />

      <div className="buttons">
        <Button
          variant={card ? 'contained' : 'outlined'}
          color={card ? 'secondary' : 'primary'}
          className="MuiButton-colored"
          size="medium"
          onClick={handleAddCard}
        >
          {card ? 'Change card' : 'Add card'}
        </Button>
      </div>

      {!!component && (
        <Modal title={normalize(component)} size="medium" show={visible} onClose={handleCloseModal}>
          {!isNil(component) && <ModalComponent onClose={handleCloseModal} />}
        </Modal>
      )}
    </>
  );
};

export default PaymentInfo;

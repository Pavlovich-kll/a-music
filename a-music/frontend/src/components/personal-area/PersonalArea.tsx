import React, { useMemo, useState } from 'react';
import Modal from '../../ui/modal';
import ChangeAvatar from './modal-components/AvatarEditor';
import WrapperProfile from './styles';
import { isNil } from 'ramda';
import { normalize } from '../../helpers/string.utils';
import { useSelector } from 'react-redux';
import { MainInfo } from './MainInfo';

interface IRootState {
  user: any;
  current: object;
}

const PROFILE_COMPONENTS = {
  changeAvatar: ChangeAvatar,
};

const PersonalArea = () => {
  const currentUser = useSelector((state: IRootState) => state.user.current);
  const [visible, setVisible] = useState<boolean>(false);
  const [component, setComponent] = useState<keyof typeof PROFILE_COMPONENTS | null>(null);
  const ModalComponent = useMemo(() => PROFILE_COMPONENTS[component], [component]);

  const handleCloseModal = () => {
    setVisible(false);
    setComponent(null);
  };

  const handleChangeAvatar = () => {
    setVisible(true);
    setComponent('changeAvatar');
  };

  return (
    <WrapperProfile>
      <MainInfo currentUser={currentUser} onChangeAvatar={handleChangeAvatar} />

      {!!component && (
        <Modal title={normalize(component)} size="medium" show={visible} onClose={handleCloseModal}>
          {!isNil(component) && <ModalComponent onClose={handleCloseModal} />}
        </Modal>
      )}
    </WrapperProfile>
  );
};

export default PersonalArea;

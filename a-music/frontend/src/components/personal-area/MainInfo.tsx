import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import { HomeButton } from '../home-button/HomeButton';
import { Navigation } from './Navigation';

interface IProps {
  currentUser: any;
  onChangeAvatar: any;
}

export const MainInfo = ({ currentUser, onChangeAvatar }: IProps) => {
  return (
    <div className="mainInfo">
      <HomeButton />
      <Avatar
        src={currentUser.username ? `/user-service/images/${currentUser.avatar}` : '/static/images/avatar/1.jpg'}
        className="avatar"
        onClick={onChangeAvatar}
      />
      <div className="nickName">{currentUser.username}</div>
      <div className="fullName">{`${currentUser.firstName}  ${currentUser.lastName}`}</div>
      <Navigation />
    </div>
  );
};

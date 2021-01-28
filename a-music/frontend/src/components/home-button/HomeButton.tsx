import React from 'react';
import IconButton from '@material-ui/core/IconButton';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import Tooltip from '@material-ui/core/Tooltip';
import { useHistory } from 'react-router-dom';
import { HOME_PATH } from '../../router';

export const HomeButton = () => {
  const { push } = useHistory();
  const handleClickHome = () => {
    push(HOME_PATH);
  };

  return (
    <Tooltip title="Back to home page">
      <IconButton className="homeButton" onClick={handleClickHome}>
        <ArrowBackIcon />
      </IconButton>
    </Tooltip>
  );
};

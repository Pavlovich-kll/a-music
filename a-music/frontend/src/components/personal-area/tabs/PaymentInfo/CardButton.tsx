import React from 'react';
import { Button } from '@material-ui/core';

interface IProps {
  card: object;
}

const CardButton = (card: IProps) => {
  return (
    <Button variant={'contained'} color={'primary'} size="medium" type="submit" fullWidth>
      {card ? 'Change card' : 'Add card'}
    </Button>
  );
};

export default CardButton;

import React from 'react';
import { InputAdornment, OutlinedInput, FormControl, IconButton } from '@material-ui/core';
import { Visibility, VisibilityOff } from '@material-ui/icons';

interface passwordInputProps {
  id: string;
  labelWidth: number;
  label?: string;
  error: boolean;
  value: string;
  name: string;
  placeholder: string;
  onChange: (e: React.ChangeEvent) => void;
  className?: string;
  children?: React.Factory<any>;
}

const PasswordInput = ({ children, ...values }: passwordInputProps) => {
  const [visibility, setVisibility] = React.useState(false);

  const visibilityToggle = () => {
    setVisibility(!visibility);
  };

  return (
    <FormControl variant="outlined" size="medium">
      <OutlinedInput
        {...values}
        type={visibility ? 'text' : 'password'}
        endAdornment={
          <InputAdornment position="end">
            <IconButton className="toggle-pass-visibility" onClick={visibilityToggle}>
              {visibility ? <Visibility /> : <VisibilityOff />}
            </IconButton>
          </InputAdornment>
        }
      />
    </FormControl>
  );
};

export default PasswordInput;

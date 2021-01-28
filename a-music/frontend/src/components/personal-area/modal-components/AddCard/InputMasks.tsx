import React from 'react';
import MaskedInput from 'react-text-mask';

export const NumberMask = (props: any) => {
  const { inputRef, ...other } = props;
  return (
    <MaskedInput
      {...other}
      ref={(ref: any) => {
        inputRef(ref ? ref.inputElement : null);
      }}
      mask={[
        /\d/,
        /\d/,
        /\d/,
        /\d/,
        ' ',
        /\d/,
        /\d/,
        /\d/,
        /\d/,
        ' ',
        /\d/,
        /\d/,
        /\d/,
        /\d/,
        ' ',
        /\d/,
        /\d/,
        /\d/,
        /\d/,
        /\d/,
        /\d/,
        /\d/,
      ]}
      placeholderChar={'\u2000'}
      guide={false}
    />
  );
};

export const ExpiryMask = (props: any) => {
  const { inputRef, ...other } = props;
  return (
    <MaskedInput
      {...other}
      ref={(ref: any) => {
        inputRef(ref ? ref.inputElement : null);
      }}
      mask={[/\d/, /\d/, '/', /\d/, /\d/]}
      placeholderChar={'\u2000'}
      guide={false}
    />
  );
};

export const CvcMask = (props: any) => {
  const { inputRef, ...other } = props;
  return (
    <MaskedInput
      {...other}
      ref={(ref: any) => {
        inputRef(ref ? ref.inputElement : null);
      }}
      mask={[/\d/, /\d/, /\d/, /\d/]}
      placeholderChar={'\u2000'}
      guide={false}
    />
  );
};

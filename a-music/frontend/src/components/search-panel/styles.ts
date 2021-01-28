import styled from 'styled-components';

const applyDefaultStyles = (styles: any) => ({
  ...styles,
  cursor: 'pointer',
});

export const customStyles = {
  option: (styles: any, { isFocused }: any) => ({
    ...styles,
    cursor: 'pointer',
    backgroundColor: isFocused ? 'grey' : null,
    color: 'black',
  }),
  dropdownIndicator: applyDefaultStyles,
  clearIndicator: applyDefaultStyles,
  valueContainer: (styles: any) => ({
    ...styles,
    cursor: 'text',
  }),
};

export const SearchView = styled.div`
  input {
    opacity: 1 !important;
  }
`;

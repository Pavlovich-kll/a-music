import styled from 'styled-components';
import { Form } from 'formik';

export const FormWrapper = styled(Form)`
  max-height: 80vh;
  overflow: auto;

  .error {
    color: red;
    margin-left: 0.5rem;
    margin-bottom: 0.5rem;
    font-size: 0.8rem;
  }
  .MuiButtonBase-outlined {
    margin-top: 1rem;
  }
  .MuiFormControl-root {
    margin-bottom: 0.5rem;
    margin-top: 0.3rem;
    width: 100%;
  }
`;

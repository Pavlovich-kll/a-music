import styled from 'styled-components';
import { Form } from 'formik';
import PhoneInput from 'react-phone-input-2';

export const WrappedForm = styled(Form).attrs({
  className: 'AuthenticateWrappedForm',
})`
  display: flex;
  flex-direction: column;
  padding: 40px;

  .MuiTextField-root {
    margin-bottom: 1rem;
    border-radius: 50px;
  }

  .container-for-inputs {
    display: flex;
    align-items: center;
    margin-bottom: 40px;
  }

  .container-for-recovery-pass {
    display: flex;
    flex-direction: column;
    align-items: baseline;
  }

  .container-for-buttons {
    display: flex;
    flex-direction: column;
  }

  .container-for-pass-recovery {
    margin-bottom: 20px;
  }

  .span-pass-recovery {
    margin-bottom: 40px;
    font-size: 16px;
    line-height: 160%;
    margin-top: -20px;
    color: #556170;
  }

  .phone-input {
    width: 290px;
    border-radius: 0;
    border: 0;
    border-bottom: 1px solid #adadad;
    padding: 10px 10px 10px 60px;
    font-size: 16px;
    line-height: 160%;
    color: #556170;
    ::placeholder {
      color: #adadad;
    }
  }

  .react-tel-input .form-control:focus {
    border-color: #fff;
    border-bottom: 1px solid #adadad;
    box-shadow: 0 0 0 0 #fff;
  }

  .react-tel-input .form-control:hover {
    ::placeholder {
      color: #8c8c8c !important;
    }
  }

  .MuiInput-underline:hover:not(.Mui-disabled):before {
    border-bottom: 1px solid rgba(0, 0, 0, 0.87);
  }

  .flag-dropdown::before {
    content: '';
    display: none !important;
  }

  .error {
    color: ${({ theme: { colors } }) => colors.primary.brandColor};
    padding: 0px 14px 20px;
  }

  .phone-error {
    border-bottom: 1px solid ${({ theme: { colors } }) => colors.primary.brandColor};
  }

  .phone-error-text {
    color: ${({ theme: { colors } }) => colors.primary.brandColor};
    margin-right: 14px;
    margin-top: 3px;
    font-size: 0.75rem;
    text-align: left;
    font-weight: 400;
    line-height: 1.66;
    letter-spacing: 0.03333em;
    position: absolute;
    bottom: -20px;
  }
  .special-label {
    display: none;
  }
  div {
    position: relative;
  }
  div .phone-error-text {
    position: absolute;
    bottom: -20px;
  }
  #outlined-password-helper-text {
    position: absolute;
    bottom: -20px;
  }

  .password {
    width: 290px;
    margin: 0 0 0 60px;
    input:first-of-type {
      width: 290px;
      border-radius: 0;
      border: 0;
      padding: 10px;
      font-size: 16px;
      line-height: 160%;
      color: #556170;
    }
    label:first-of-type {
      color: #adadad;
    }
  }

  .pass-recovery {
    margin: 0 0 40px 0;
  }

  .password input:first-of-type:hover {
    ::placeholder {
      color: #8c8c8c !important;
    }
  }
  .MuiInput-underline:before:focus {
    border-bottom: 1px solid ${({ theme: { colors } }) => colors.primary.brandColor} !important;
  }

  .container-for-remember:hover {
    color: ${({ theme: { colors } }) => colors.primary.brandColor};
  }

  .password-error {
    border-bottom: 1px solid ${({ theme: { colors } }) => colors.primary.brandColor};
  }

  .pass-error {
    .MuiInputBase-root {
      border-bottom: 1px solid ${({ theme: { colors } }) => colors.primary.brandColor} !important;
    }
  }

  .auth-button {
    background-color: ${({ theme: { colors } }) => colors.primary.brandColor};
    text-transform: none;
    width: 190px;
    height: 48px;
    border-radius: 0;
  }

  .auth-button:hover {
    background-color: ${({ theme: { colors } }) => colors.primary.bcHover};
  }

  .password-recover {
    margin-top: 20px;
    text-transform: none;
    width: 113px;
    border: 0;
    background-color: white;
    border-radius: 0;
    border-bottom: 1px solid black;
    padding: 0;
    line-height: 160%;
    color: #556170;
    display: flex;
    justify-content: flex-start;
    span:first-of-type {
      font-style: normal;
      font-weight: 500;
      font-size: 13px;
    }
  }

  .password-recover:hover {
    color: #feda00;
    border-bottom: 1px solid #feda00;
    background-color: white;
  }

  .MuiInputBase-root {
    border-bottom: 1px solid #adadad !important;
  }

  .MuiInputBase-root:hover {
    border-bottom: 1px solid #8c8c8c !important;
  }

  .MuiInput-underline:after {
    content: '';
    display: none !important;
  }

  .MuiInputBase-root:before {
    content: '';
    display: none !important;
  }

  .Mui-error {
    color: ${({ theme: { colors } }) => colors.primary.brandColor} !important;
  }

  .toggle-pass-visibility {
    color: ${({ theme: { colors } }) => colors.primary.brandColor};
  }

  .phone-check-submit {
    background-color: ${({ theme: { colors } }) => colors.primary.brandColor};
    border-radius: 0;
    margin-top: 1rem;
    width: 190px;
    height: 48px;
  }

  .phone-check-submit:hover {
    background-color: ${({ theme: { colors } }) => colors.primary.bcHover};
  }

  .MuiFormControl-root {
    margin-bottom: 0 !important;
  }

  .custom-checkbox {
    position: absolute;
    z-index: -1;
    opacity: 0;
  }

  .custom-checkbox + label {
    display: inline-flex;
    align-items: center;
    user-select: none;
    font-size: 14px;
    line-height: 160%;
    font-weight: 500;
  }

  .custom-checkbox + label::before {
    content: '';
    display: inline-block;
    width: 22px;
    height: 22px;
    flex-shrink: 0;
    flex-grow: 0;
    border: 1px solid #adb5bd;
    margin-right: 0.5em;
    background-repeat: no-repeat;
    background-position: center center;
    background-size: 50% 50%;
  }

  .custom-checkbox + label::before::hover {
    border-color: #ffe963;
  }

  .custom-checkbox:checked + label::before {
    border-color: #ffe963;
    background-color: #ffe963;
    background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 8 8'%3e%3cpath fill='%23fff' d='M6.564.75l-3.59 3.612-1.538-1.55L0 4.26 2.974 7.25 8 2.193z'/%3e%3c/svg%3e");
  }

  .container-for-remember {
    margin-top: 20px;
    width: 130px;
  }

  .container-for-sign-up {
    margin: 20px 0 20px 0;
    span:first-of-type {
      font-size: 14px;
      line-height: 160%;
      color: #000000;
    }

    a:first-of-type {
      font-size: 14px;
      line-height: 160%;
      color: #556170;
      border-bottom: 1px solid;
    }
  }
`;

export const StyledPhoneInput = styled(PhoneInput)`
  width: 290px;
  position: static;
`;

export const SpanTitle = styled.span`
  color: ${({ theme: { colors } }) => colors.primary.baseText};
  font-size: 30px;
  line-height: 35px;
`;

export const SpanText = styled.span`
  color: ${({ theme: { colors } }) => colors.secondary.grayText};
  font-size: 17px;
  line-height: 20px;
`;

export default styled.div``;

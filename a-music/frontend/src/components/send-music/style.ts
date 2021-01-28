import styled from 'styled-components';
import { Button } from 'antd';
import { Typography } from '@material-ui/core';
import { Form } from 'formik';

export const WrappedForm = styled(Form)`
  margin: 20px;
  padding: 20px;
  width: 100%;
  border-radius: 0.5rem;
  background-color: ${({ theme: { colors } }) => colors.primary.white};
  position: relative;
  color: white;

  .MuiFormControl-root {
    display: flex;
    flex-direction: column;
    margin-bottom: 1rem;
    border-radius: 50px;
    width: 100%;
    input:first-of-type {
      background-color: ${({ theme: { colors } }) => colors.secondary.whiteText};
    }
  }

  .MuiTypography-root {
    text-align: center;
    color: ${({ theme: { colors } }) => colors.secondary.whiteText};
  }

  .MuiButton-root {
    margin-top: 20px;
  }

  .label {
    display: flex;
    justify-content: space-around;
    margin-bottom: 20px;
  }

  .input {
    display: none;
  }

  .status-input {
    margin: 20px 0;
    color: gray;
  }

  .picture {
    margin-right: 30px;
  }

  .button {
    display: block;
    margin: 20px 0 10px;
    background-color: ${({ theme: { colors } }) => colors.primary.white};
    border: 2px solid ${({ theme: { colors } }) => colors.primary.brandColor};
    color: ${({ theme: { colors } }) => colors.primary.baseText};
  }

  .button:hover {
    background-color: ${({ theme: { colors } }) => colors.primary.bcHover};
    border: 2px solid ${({ theme: { colors } }) => colors.primary.bcHover};
  }

  button:active {
    background-color: ${({ theme: { colors } }) => colors.primary.bcClicked};
    border: 2px solid ${({ theme: { colors } }) => colors.primary.bcClicked};
  }

  .send {
    margin: 10px 0 20px;
    margin-left: calc(50% - 95px);
    width: 190px;
    border: none;
    background-color: ${({ theme: { colors } }) => colors.primary.brandColor};
    color: ${({ theme: { colors } }) => colors.primary.baseText};
  }

  .send:hover {
    background-color: ${({ theme: { colors } }) => colors.primary.bcHover};
    border: none;
  }

  send:active {
    background-color: ${({ theme: { colors } }) => colors.primary.bcClicked};
  }

  .upload {
    margin-top: 10px;
    background-color: ${({ theme: { colors } }) => colors.primary.white};
    border: 2px solid ${({ theme: { colors } }) => colors.primary.brandColor};
    color: ${({ theme: { colors } }) => colors.primary.baseText};
  }

  .upload:hover {
    background-color: ${({ theme: { colors } }) => colors.primary.bcHover};
    border: 2px solid ${({ theme: { colors } }) => colors.primary.bcHover};
  }

  .upload:active {
    background-color: ${({ theme: { colors } }) => colors.primary.bcClicked};
    border: 2px solid ${({ theme: { colors } }) => colors.primary.bcClicked};
  }

  .error-field {
    color: ${({ theme: { colors } }) => colors.primary.brandColor};
    margin: 3px 14px 10px;
    font-size: 0.75rem;
    text-align: left;
    font-weight: 400;
    line-height: 1.66;
    letter-spacing: 0.03333em;
  }

  .MuiInputLabel-filled {
    transform: translate(12px, 10px) scale(0.75);
  }

  .MuiSelect-root {
    background-color: ${({ theme: { colors } }) => colors.secondary.whiteText};
  }

  .MuiSelect-root:focus {
    background-color: ${({ theme: { colors } }) => colors.secondary.whiteText};
  }
`;
export const HideButton = styled(Button)`
  position: absolute;
  top: 30px;
  left: 20px;
  border-radius: 50%;
  font-size: 10px;
  height: 40px;
  width: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${({ theme: { colors } }) => colors.primary.brandColor} !important;
  border: none;
  z-index: 3;
`;

export const TitleTypography = styled(Typography)`
  text-align: left !important;
  font-size: 20px !important;
`;

export const TitleSmallTypography = styled(TitleTypography)`
  margin-top: 55px !important;
  margin-bottom: 20px !important;
  display: block;
`;

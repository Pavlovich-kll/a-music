import styled from 'styled-components';
import Button from '@material-ui/core/Button';
import Dark from './assets/Logo/Dark.png';
import Light from './assets/Logo/Light.png';

export default styled.header`
  z-index: 3;
  display: grid;
  grid-template-columns: 1fr 9fr 1fr;
  align-items: center;
  background-color: ${({ theme: { colors } }) => colors.primary.white};
  color: ${({ theme: { secondaryColor } }) => secondaryColor};
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);

  img {
    height: 90%;
    width: auto;
    object-fit: contain;
  }

  .user-area {
    &:focus {
      outline: 1px solid ${({ theme: { colors } }) => colors.primary.baseText};
      outline-offset: 1px;
    }
  }

  .auth-control {
    justify-self: end;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .username {
    text-align: end;
    margin-left: 15px;
    color: ${({ theme: { colors } }) => colors.primary.baseText};
  }

  .logo {
    width: 100%;
    margin-right: 20px;
    &:focus {
      outline: 1px solid ${({ theme: { colors } }) => colors.primary.baseText};
      outline-offset: 5px;
    }
  }

  .logo-img {
    background-image: url(${({ theme: { icon } }) => (icon === 'dark' ? Dark : Light)});
    height: 40px;
    width: 124px;
  }

  .ant-btn-link {
    color: ${({ theme: { colors } }) => colors.primary.brandColor};
  }

  .link {
    justify-self: end;
    margin: 0 20px;
    background: ${({ theme: { colors } }) => colors.primary.white};
    color: ${({ theme: { colors } }) => colors.primary.baseText};
    border: 2px solid ${({ theme: { colors } }) => colors.primary.brandColor};
    padding: 10px;
    font-weight: 500;
    white-space: nowrap;
    &:focus {
      outline: 1px solid ${({ theme: { colors } }) => colors.primary.baseText};
      outline-offset: -7px;
    }
    &:hover {
      background-color: ${({ theme: { colors } }) => colors.primary.brandColor};
      border: 2px solid ${({ theme: { colors } }) => colors.primary.brandColor};
    }
  }

  .logout-link {
    color: ${({ theme: { colors } }) => colors.primary.brandColor};
    &:hover {
      color: ${({ theme: { colors } }) => colors.primary.bcHover};
    }
  }

  .logout-link-elem {
    &:focus {
      outline: 1px solid ${({ theme: { colors } }) => colors.primary.baseText};
      outline-offset: 10px;
    }
  }
`;

export const RadioButton = styled(Button)`
  background-color: ${({ theme: { colors } }) => colors.primary.brandColor} !important;
  margin-right: 20px !important;
  width: 130px;

  &:hover {
    background-color: ${({ theme: { colors } }) => colors.primary.bcHover} !important;
  }
  .radio-link {
    color: ${({ theme: { colors } }) => colors.primary.baseText};
  }
`;

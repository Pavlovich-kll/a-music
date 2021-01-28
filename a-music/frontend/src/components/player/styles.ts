import styled from 'styled-components';
import { Button } from 'antd';

export default styled.div`
  height: 100%;
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;
`;

export const LoginButton = styled(Button)`
  margin-right: 20px;
  width: 128px;
  height: 48px;
  border-radius: 0;
  border: 2px solid #feda00;
  z-index: 3;
  background-color: ${({ theme: { colors } }) => colors.primary.brandColor};
  color: #020303;
  transition: 300ms;

  :hover {
    background-color: ${({ theme: { colors } }) => colors.primary.bcHover};
    border: 2px solid transparent;
    color: #020303;
    transform: scale(1.05);
  }

  :focus {
    background-color: ${({ theme: { colors } }) => colors.primary.bcHover};
    border: 2px solid #feda00;
    color: #020303;
    transform: scale(1.03);
    outline:2px solid #18fbfb;
`;

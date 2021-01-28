import styled from 'styled-components';
import { Link } from 'react-router-dom';

export default styled.div`
  width: 100%;
  .error-container {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    margin: 10rem 0 0 10rem;
    font-family: Roboto;
    font-style: normal;
    color: ${({ theme: { colors } }) => colors.primary.baseText};
  }
  .error-header h1 {
    font-weight: 300;
    font-size: 66px;
    line-height: 130%;
  }
  .error-description {
    margin-top: 40px;
    font-size: 18px;
    line-height: 160%;
  }
`;
export const LinkStyled = styled(Link)`
  color: ${({ theme: { colors } }) => colors.primary.baseText};
  font-weight: 500;
  font-size: 14px;
  line-height: 160%;
  text-decoration: none;
  border-bottom: 1px solid ${({ theme: { colors } }) => colors.primary.baseText};
`;

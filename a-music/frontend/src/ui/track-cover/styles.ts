import styled from 'styled-components';

export default styled.div`
  display: flex;
  margin: 0 40px 0 50px;

  img {
    width: 48px;
    height: 48px;
    margin: 8px 20px 0 0;
    border-radius: 4px;
  }

  .container-text {
    width: 125px;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }

  .span-title {
    font-size: 16px;
    line-height: 160%;
    color: ${({ theme: { colors } }) => colors.primary.baseText};
  }

  .span-author {
    font-size: 14px;
    line-height: 160%;
    color: #828282;
  }
`;

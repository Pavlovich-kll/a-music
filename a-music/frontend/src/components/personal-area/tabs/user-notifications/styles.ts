import styled from 'styled-components';

export const NotificationsWrapper = styled.div`
  max-height: 80vh;
  overflow: auto;

  .containers {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 30px 0 15px;
  }

  .block {
    margin-bottom: 60px;
  }

  .chapter {
    display: flex;
    border-bottom: grey 1px solid;
    display: flex;
    justify-content: space-between;
    margin-bottom: 30px;
  }

  .right-container {
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 40%;
  }

  .left-container {
    width: 60%;
  }

  .chapter-text {
    width: 95%;
  }

  .block-title {
    font-size: 1.5rem;
  }

  .chapter-text,
  .checkbox-header,
  .chapter-text {
    color: grey;
  }

  .checkbox {
  }

  .checkbox-header {
    width: 100px;
    text-align: center;
    margin-left: 10px;
    margin-right: 10px;
  }
`;

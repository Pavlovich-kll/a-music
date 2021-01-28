import styled from 'styled-components';

export default styled.div`
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: hidden;

  main {
    width: 100%;
    height: 100%;
    overflow-y: hidden;
    background-color: ${({ theme: { colors } }) => colors.secondary.cream};
  }

  .landing-main {
    position: absolute;
    display: block;
    background-color: #020d1c;
    width: 100%;
    height: 100%;
  }

  .content {
    display: flex;
    justify-content: center;
    align-items: start;
    height: 100%;
    overflow-y: auto;
  }

  @media (max-width: 1024px) {
    main {
      grid-template-columns: 2fr 7fr;
    }
  }

  @media (max-width: 768px) {
    main {
      grid-template-columns: 1fr;
    }

    .content {
      padding: 0;
    }
  }
`;

import styled from 'styled-components';

export const SuspenseComponent = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export default styled.div`
  display: grid;
  grid-template-rows: 60px 1fr;
  height: 100vh;
  overflow: hidden;

  main {
    width: 100%;
    display: grid;
    grid-template-columns: 275px 5fr;
    grid-gap: 8px;
    grid-template-rows: 1fr;
    height: 100%;
    overflow-y: hidden;
    background-color: ${({ theme: { colors } }) => colors.secondary.cream};
  }

  .landing-main {
    position: absolute;
    display: block;
    background-color: #020d1c;
  }

  .content {
    padding: 0 2rem;
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

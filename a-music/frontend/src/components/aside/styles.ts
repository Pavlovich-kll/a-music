import styled from 'styled-components';

export default styled.aside`
  display: flex;
  flex-direction: column;
  padding: 170px 0 0 50px;
  overflow: hidden;
  width: 100%;
  background-color: ${({ theme: { colors } }) => colors.secondary.creamDark};
  color: ${({ theme: { colors } }) => colors.secondary.textUnvisible};

  .nav-item {
    padding-top: 20px;
    position: relative;
  }

  .nav-border {
    display: flex;
    border-bottom: 1px solid ${({ theme: { colors } }) => colors.primary.baseText};
    width: 150px;
    padding-bottom: 19px;
  }

  .nav-border-none {
    border-bottom: none;
  }

  .nav-name {
    font-size: 16px;
    line-height: 160%;
    margin-left: 22px;
    color: ${({ theme: { colors } }) => colors.primary.baseText};
  }

  .nav-svg {
    circle,
    path {
      stroke: ${({ theme: { colors } }) => colors.primary.baseText};
    }
  }

  .radio-link {
    color: ${({ theme: { colors } }) => colors.primary.baseText};
  }

  .active-block {
    display: none;
    position: absolute;
    right: 0;
    top: 9px;
    width: 5px;
    height: 49px;
    background: #feda00;
    box-shadow: 0px 4px 25px rgba(2, 3, 3, 0.03);
    border-radius: 0px 60px 60px 0px;
    transform: rotate(-180deg);
  }

  .active-item {
    cursor: pointer;
    .nav-border {
      border-color: ${({ theme: { colors } }) => colors.primary.bcClicked};
    }
    .nav-svg {
      circle,
      path {
        stroke: ${({ theme: { colors } }) => colors.primary.bcClicked};
      }
    }
    .nav-name,
    .radio-link {
      color: ${({ theme: { colors } }) => colors.primary.bcClicked};
    }
    .active-block {
      display: block;
    }
  }

  .nav-item:focus {
    cursor: pointer;
    .nav-border {
      border-color: ${({ theme: { colors } }) => colors.primary.bcClicked};
    }
    .nav-svg {
      circle,
      path {
        stroke: ${({ theme: { colors } }) => colors.primary.bcClicked};
      }
    }
    .nav-name,
    .radio-link {
      color: ${({ theme: { colors } }) => colors.primary.bcClicked};
    }
  }

  .nav-item:hover {
    cursor: pointer;
    .nav-border {
      border-color: ${({ theme: { colors } }) => colors.primary.brandColor};
    }
    .nav-svg {
      circle,
      path {
        stroke: ${({ theme: { colors } }) => colors.primary.brandColor};
      }
    }
    .nav-name,
    .radio-link {
      color: ${({ theme: { colors } }) => colors.primary.brandColor};
    }
  }

  header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid;
    padding: 0 2rem;

    h5 {
      margin: 0;
    }
  }

  .aside-content {
    height: inherit;
    overflow-y: auto;
    display: block;
    padding: 0;

    ul {
      padding: 0;
    }
  }

  .MuiListItemText-secondary {
    color: #ffab00;
  }

  @media (max-width: 768px) {
    display: none;
  }
`;

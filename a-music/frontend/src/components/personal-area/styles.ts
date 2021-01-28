import styled from 'styled-components';

export default styled.div`
  width: 100%;

  .mainInfo {
    display: flex;
    position: relative;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    color: #fff;
    margin-top: 10px;
    padding-top: 40px;
    border-radius: 5px;
    box-shadow: 0 5px 28px rgba(0, 0, 0, 0.25), 0 5px 5px rgba(0, 0, 0, 0.22);
    background: linear-gradient(to right, #16222a, #3a6073);

    .block-button {
      margin: 30px 0 30px 20px;
    }

    .MuiPaper-elevation1 {
      width: 100%;
    }

    .homeButton {
      position: absolute;
      color: ${({ theme: { colors } }) => colors.primary.brandColor};
      top: 5px;
      left: 5px;
    }

    .homeButton:hover {
      color: ${({ theme: { colors } }) => colors.primary.bcHover};
    }

    .avatar {
      position: relative;
      transition-duration: 0.4s;
      width: 200px;
      height: 200px;
      border-radius: 50%;
      box-shadow: 0 5px 28px rgba(0, 0, 0, 0.25), 0 5px 5px rgba(0, 0, 0, 0.22);
    }
    .avatar:hover {
      cursor: pointer;
      opacity: 0.7;
    }

    .nickName {
      padding-top: 10px;
      font-size: 32px;
      font-weight: bold;
    }

    .fullName {
      font-size: 24px;
      margin-bottom: 50px;
      line-height: 1;
    }

    .personalNav {
      display: flex;
      justify-content: space-around;
      align-items: center;
      width: 100%;
      list-style: none;
      padding: 0;
      margin: 0;

      li {
        font-size: 16px;
        line-height: 1;
        font-weight: bold;
        padding-bottom: 2px;
        border-bottom: 3px solid transparent;
      }
      li:hover {
        cursor: pointer;
      }

      .active {
        border-bottom: 3px solid #fff;
      }
    }
  }

  .tab {
    margin-top: 20px;
    margin-bottom: 20px;
    width: 100%;
    padding: 50px;
    align-items: center;

    .flexWrapper {
      margin: 0 auto;
      display: flex;
      align-items: center;
      justify-content: space-between;

      .itemTitle {
        font-weight: 900;
      }

      .flex {
        width: 80%;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .wrapperButton {
          background: none;
          border: none;
          margin: 0;
          padding: 0;
        }

        .wrapperButton > button {
          color: ${({ theme: { colors } }) => colors.primary.brandColor};
        }

        .wrapperButton > button:hover {
          color: ${({ theme: { colors } }) => colors.primary.bcHover};
        }

        .form-control {
          height: 30px;
        }

        .closeButton:hover {
          color: #f4511e;
        }
        .submitButton:hover {
          color: #7cb342;
        }

        .MuiFormHelperText-contained {
          position: absolute;
          width: 100%;
          bottom: -20px;
        }
      }

      .changeButton {
        color: ${({ theme: { colors } }) => colors.primary.brandColor};
      }

      .changeButton:hover {
        color: ${({ theme: { colors } }) => colors.primary.bcHover};
      }
    }

    .buttons {
      margin: 30px auto;
      text-align: center;
    }

    .open-modal {
      margin-left: -0.7%;
    }
    .MuiButton-label {
      text-transform: none;
    }
  }
  .MuiButton-colored {
    background-color: transparent;
    border: 2px solid ${({ theme: { colors } }) => colors.primary.bcHover};
    color: black;
    cursor: pointer;
  }
  .MuiButton-colored:hover {
    background-color: ${({ theme: { colors } }) => colors.primary.bcHover};
    color: black;
  }
`;

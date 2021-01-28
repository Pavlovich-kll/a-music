import styled from 'styled-components';

export default styled.div`
  .cardBlock {
    div {
      margin: 0;
      padding: 0;
    }

    .mastercard {
      background: linear-gradient(to right, #000000, #434343);
    }
    .american-express {
      background: linear-gradient(to right, #757f9a, #d7dde8);
    }
    .visa {
      background: linear-gradient(to right, #2bc0e4, #eaecc6);
    }
    .discover {
      background: linear-gradient(to right, #334d50, #cbcaa5);
    }
    .mir {
      background: linear-gradient(to right, #2193b0, #6dd5ed);
    }
    .maestro {
      background: linear-gradient(to right, #bdc3c7, #2c3e50);
    }

    .card {
      position: relative;
      margin: 0 auto;
      padding: 20px 50px;
      width: 530px;
      height: 300px;
      border-radius: 20px;
      line-height: 1;
      color: #fff;
      box-shadow: 0 5px 28px rgba(0, 0, 0, 0.25), 0 5px 5px rgba(0, 0, 0, 0.22);

      &-logo {
        position: absolute;
        top: 10px;
        right: 10px;
        width: 150px;
      }
      &-chip {
        margin-top: 60px;
        width: 90px;
      }

      &-number {
        text-align: center;
        font-size: 40px;
        font-family: 'Share Tech Mono', monospace;
      }

      &-expiry {
        display: flex;
        justify-content: flex-end;
        align-items: flex-end;

        .expiry-tooltip {
          color: #f5f5f5;
          text-transform: uppercase;
          font-size: 12px;
        }

        .valid {
          width: 40px;
          height: 32px;
          margin-right: 10px;
        }

        .expiry-flex {
          display: flex;
          flex-direction: column;
        }

        .expiry {
          font-size: 26px;
          font-family: 'Share Tech Mono', monospace;
        }
      }

      &-name {
        font-size: 26px;
        text-transform: uppercase;
        font-family: 'Share Tech Mono', monospace;
      }
    }
  }
`;

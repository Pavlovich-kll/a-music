import styled from 'styled-components';
import fon from './assets/fon-large.jpg';
import { LoginButton } from '../player/styles';

export default styled.div`
  background: linear-gradient(0deg, #020d1c 21.55%, rgba(85, 97, 112, 0) 61.95%),
    linear-gradient(180deg, rgba(2, 13, 28, 0) 76.53%, rgba(2, 13, 28, 0.537037) 96.93%, #020d1c 100%), url(${fon});
  background-repeat: no-repeat;
  background-size: 100%;
  object-fit: cover;
  width: 100%;
  height: 100%;

  .container {
    max-width: 1540px;
    margin: 0 auto;
    padding: 0 30px;
  }

  .landing-footer {
    background-color: #020303;
  }

  .container-footer {
    display: flex;
    flex-direction: row;
    padding: 40px 0 40px 180px;
    max-width: 1540px;
    margin: 0 auto;
  }

  .landing-footer-span {
    font-size: 12px;
    line-height: 160%;
    color: #ffffff;
    opacity: 0.35;
    display: block;
    margin-right: 40px;
  }

  .block-title {
    color: #fff;
    display: flex;
    flex-direction: column;
    margin-top: 313px;
    margin-left: 115px;
  }

  .title-main {
    font-size: 56px;
    line-height: 130%;
    width: 460px;
  }

  .description-main {
    margin-top: 20px;
    font-weight: 300;
    font-size: 24px;
    line-height: 130%;
  }

  .arrow-7 {
    margin-left: calc(50% - 25px / 2);
  }

  .arrow-7 span {
    display: block;
    width: 25px;
    height: 25px;
    border-bottom: 1px solid #feda00;
    border-right: 1px solid #feda00;
    transform: rotate(45deg);
    margin: -16px;
    animation: arrow-7 3s infinite;
  }

  .arrow-7 span:nth-child(2) {
    animation-delay: -0.3s;
  }

  .arrow-7 span:nth-child(1) {
    animation-delay: -0.5s;
  }

  @keyframes arrow-7 {
    0% {
      opacity: 0;
    }
    50% {
      opacity: 1;
    }
    100% {
      opacity: 0;
    }
  }

  .block-info {
    margin: 72px 0 100px 0;
    background-color: #fff;
    display: flex;
    flex-direction: row;
    color: #000000;
  }

  .left-block {
    padding: 100px 90px 100px 115px;
    width: 50%;
  }

  .play-icon {
    margin-bottom: 60px;
  }

  .description-left-title {
    font-size: 42px;
    line-height: 130%;
    margin-bottom: 40px;
    display: block;
  }

  .description-left-span {
    font-size: 18px;
    line-height: 160%;
    display: block;
  }

  .right-block {
    width: 50%;
    background-color: #fafafa;
    display: flex;
    flex-direction: column;
    padding: 100px 115px 100px 60px;
  }

  .block-right-text {
    display: flex;
    flex-direction: column;
    margin-bottom: 40px;
    color: #020303;
  }

  .title-right-span {
    font-size: 16px;
    line-height: 160%;
    font-weight: bold;
    text-transform: uppercase;
  }

  .text-right-span {
    font-size: 16px;
    line-height: 160%;
  }
`;

export const SignUpLandingButton = styled(LoginButton)`
  margin: 60px 0;
  width: 190px;
`;

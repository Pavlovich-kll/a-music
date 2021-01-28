import React from 'react';
import { Button } from '@material-ui/core';
import styled from 'styled-components';
import welcome from './assets/welcome.png';
import { IModalChild } from '../../ui/modal';

const CongratulationView = styled.div`
  display: flex;

  .container-for-text {
    width: 50%;
    display: flex;
    flex-direction: column;
    padding: 60px 0 0 40px;
    margin-right: 20px;
  }

  .title-text {
    margin-top: 60px;
    font-weight: 300;
    font-size: 36px;
    line-height: 130%;
    color: #020303;
  }

  .description-text {
    margin-top: 60px;
    margin-bottom: 40px;
    font-weight: 300;
    font-size: 24px;
    line-height: 130%;
    color: #020303;
  }

  .login {
    background-color: ${({ theme: { colors } }) => colors.primary.brandColor};
    border-radius: 0;
    width: 190px;
    height: 48px;
    margin-bottom: 85px;
  }

  .login:hover {
    background-color: ${({ theme: { colors } }) => colors.primary.bcHover};
  }

  .image-block {
    margin-left: 20px;
    width: 50%;
    height: auto;
    background-image: url(${welcome});
  }

  .modal-view-btn {
    margin: 0 !important;
    font-size: 0 !important;
    position: absolute;
    top: 0px;
    right: 0px;
    padding: 20px;
    font-weight: 300;
    align-self: end;
    background-color: #ffffff !important;
    border: none;
    outline: none;
    cursor: pointer;
  }

  .modal-view-btn:hover {
    background-color: #efefef !important;
    path {
      stroke: #020303;
    }
  }

  .modal-view-btn:active {
    path {
      stroke: #feda00;
    }
  }
`;

const Congratulations = ({ nextStep }: IModalChild) => {
  const handleClickLogin = () => {
    nextStep(true);
  };

  return (
    <CongratulationView>
      <div className={'container-for-text'}>
        <span className={'title-text'}>Welcome to A-Music</span>
        <span className={'description-text'}>
          Now you can enjoy the music it is a long established fact that a reader will be distracted by the readable
          content of a page when looking at its layout.
        </span>
        <Button size="medium" type="submit" className="login" fullWidth onClick={handleClickLogin}>
          Login
        </Button>
      </div>
      <div className={'image-block'}></div>
    </CongratulationView>
  );
};

export default Congratulations;

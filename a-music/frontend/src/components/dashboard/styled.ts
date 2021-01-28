import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { Button } from '@material-ui/core';
import { TitleTypography } from '../send-music/style';

export default styled.div`
  width: 100%;
  .playlist-container {
    display: flex;
    flex-direction: column;
    padding: 3rem 0;
    width: 100%;
    margin: 30px 0 30px 0;
  }
  .playlist-line {
    border: 1px solid #f1f1f1;
  }
  .playlist-content {
    width: 100%;
    margin-top: 60px;
    display: flex;
    flex-wrap: wrap;
  }
  .playlist-create {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 0 30px;
    color: #828282;
    height: 215px;
    width: 19%;
    background: #ffffff;
    box-shadow: 0px 0px 25px rgba(2, 3, 3, 0.03);
    border-radius: 4px;
    img {
      margin-bottom: 20px;
    }
  }
  .playlist-body {
    display: flex;
    flex-direction: column;
    position: relative;
    height: 273px;
    width: 19%;
    margin: 0 30px;
    margin-bottom: 40px;
    &:hover {
      .playlist-body-hover {
        opacity: 1;
      }
    }
    img {
      max-width: 100%;
    }
  }
  .playlist-img {
    box-shadow: 0px 4px 25px rgba(2, 3, 3, 0.03);
    img {
      width: 100%;
      height: 215px;
      border-radius: 4px;
    }
  }
  .playlist-title {
    margin-top: 10px;
    color: #020303;
  }
  .playlist-amount {
    font-family: Roboto;
    font-style: normal;
    font-weight: normal;
    font-size: 14px;
    line-height: 160%;
    color: #828282;
  }
  .playlist-body-hover {
    position: absolute;
    display: flex;
    justify-content: flex-end;
    align-items: flex-end;
    margin: 0 20px 80px;
    bottom: 0;
    right: 0;
    top: 0;
    left: 0;
    opacity: 0;
    transition: opacity 0.3s linear;
  }
`;

export const LinkStyled = styled(Link)`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: #828282;
  font-family: Roboto;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 160%;
`;
export const SendButton = styled(Button)`
  background-color: #4d6884 !important;
  color: white !important;
  margin: 20px 20px 0 38% !important;
`;
export const CloseButton = styled(SendButton)`
  margin: 20px 20px 0 20px !important;
`;
export const AddTrackButton = styled(SendButton)`
  position: absolute !important;
  margin: 0 !important;
`;
export const AddButton = styled(CloseButton)`
  margin: 20px 20px 20px 0 !important;
`;
export const PlaylistSpan = styled(TitleTypography)`
  margin: 10px 0 10px 0 !important;
  font-weight: bold !important;
`;
export const PlaylistButton = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 36px;
  height: 36px;
  background: #ffffff;
  box-shadow: 3px 3px 6px rgba(2, 3, 3, 0.2);
  border-radius: 34px;
  margin: 5px;
  cursor: pointer;
`;

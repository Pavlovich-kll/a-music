import styled from 'styled-components';

export const ContainerWrapper = styled.div`
  border-top: 1px solid rgba(0, 0, 0, 0.1);
  width: 100%;
  background-color: ${({ theme: { colors } }) => colors.primary.white};
  color: ${({ theme: { colors } }) => colors.secondary.grayText};
`;

export const LikeButtonWrapper = styled.div`
  display: flex;
  align-items: center;
  .span-likes {
    font-size: 14px;
    line-height: 160%;
    color: #adadad;
  }
`;

export const AdditionalControlView: any = styled.div`
  display: flex;
  align-items: center;

  .ant-btn-link {
    color: inherit;
  }

  .anticon-sound {
    line-height: 1.499;
    padding: 0 15px;
    font-size: 14px;
  }

  .MuiTooltip-tooltip-root {
    width: 300px;
    background-color: #fff;
  }

  .volume-slider {
    background-color: red;
    height: 80px;
  }
`;

export default styled.div`
  display: flex;
  height: 100%;
  max-width: 1440px;
  margin: 0 auto;

  .button-playlist {
    position: relative;
    width: 80px;
    height: 50px;
    background-color: #f9f9f9;
    border-radius: 4px;
    margin: 7px 0 5px 45px;
    .arrow-top {
      margin: 19px 31px;
    }
  }

  .button-playlist:hover {
    cursor: pointer;
    background-color: #e0e0e0;
  }
  .btn-playlist-containter {
    position: relative;
  }

  .control {
    display: flex;
    justify-content: space-between;
    width: 130px;
    margin: 20px 60px 0 0;

    .svg-back:hover {
      cursor: pointer;
      color: ${({ theme: { colors } }) => colors.primary.brandColor};
    }
  }

  .control .disabled svg {
    opacity: 0.3;
    cursor: default !important;
    user-select: none;
  }

  .volume-button {
    padding: 0;
  }

  .add-svg {
    margin-right: 20px;
  }

  .like-svg {
    margin-right: 10px;
  }

  .add-svg:hover {
    cursor: pointer;
  }

  .fill-svg:hover {
    fill: ${({ theme: { colors } }) => colors.primary.playerColor};
  }

  .add-svg:hover .svg {
    stroke: ${({ theme: { colors } }) => colors.primary.playerColor};
  }

  .icon-button {
    padding: 0;
  }

  .icon-button.active .svg {
    stroke: ${({ theme: { colors } }) => colors.primary.playerColor};
  }

  .like-button {
    padding: 0;
    color: ${({ theme: { colors } }) => colors.primary.brandColor};
  }

  .favorite-button {
    color: ${({ theme: { colors } }) => colors.primary.brandColor};
  }

  .player-slider {
    margin-top: 0 !important;
    color: ${({ theme: { colors } }) => colors.primary.brandColor};
  }

  .playerWrapper {
    width: 540px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-right: 40px;

    .play-control {
      display: flex;
      align-items: center;

      .time {
        margin-left: 20px;
        font-size: 14px;
        line-height: 160%;
        color: #828282;
      }

      .MuiSlider-root {
        grid-column-end: span 3;
        margin-right: 0;
        margin-top: 8px;
      }

      .hFivYT {
        display: flex;
        justify-content: center;
      }

      .track img {
        border-radius: 50%;
      }

      .hFivYT img {
        margin-right: 1rem;
      }
    }
  }
`;

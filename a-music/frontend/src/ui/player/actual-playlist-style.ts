import styled from 'styled-components';

export default styled.div`
  width: 433px;
  height: 614px;
  position: absolute;
  right: 0;
  top: -634px;
  background-color: #ffffff;
  overflow: hidden;

  .block-header {
    display: flex;
  }

  .span-title {
    font-size: 24px;
    line-height: 160%;
    color: #000000;
    margin: 40px 184px 40px 40px;
  }

  .svg-button {
    margin: 20px 20px 0 0;
    cursor: pointer;
  }

  .aside-content {
    height: inherit;
    overflow-y: auto;
    display: block;
    padding: 0;
    padding-bottom: 40px;
    ul {
      padding: 0;
    }
  }
`;

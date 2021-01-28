import styled from 'styled-components';
import { MODAL_SIZE, SizeType } from './constants';

interface IStylesProps {
  size: SizeType;
  show: boolean;
}

export default styled.div<IStylesProps>`
    width: 100%;
    height: 100vh;
    z-index: 100;
    background-color: rgba(0,0,0, .5);
    position: relative;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    overflow: auto;
    display: ${({ show }) => (show ? 'block' : 'none')};

    transition: display .5s ease-out;

    .modal-view-wrapper {
        width: ${({ size }) => MODAL_SIZE[size]};
        height: auto;
        position: absolute;
        top: calc(50vh - 590px / 2);
        left: calc(50% - ${({ size }) => MODAL_SIZE[size]} / 2);
        background: white;
        white-space: pre-wrap;
        overflow-wrap: break-word;
        hyphens: auto;
        overflow: auto;
    }

    .modal-view-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .modal-view-title {
        margin: 60px 0 0 40px;
        text-transform: capitalize;
        font-weight: 300;
        font-size: 36px;
        line-height: 130%;
        color: #020303;
    }

    .modal-view-btn {
        margin: 5px 20px 0 auto;
        font-weight: 300;
        font-size: 40px;
        align-self: end;
        background-color: transparent;
        border: none;
        outline: none;
        cursor: pointer;
    }
    
    .modal-view-btn:hover{
        path{
            stroke: #020303;
        }
    }
    
    .modal-view-btn:active {
      path{
            stroke: #FEDA00;
        }
    }
    
    .changeAvatarBlock {
       display: flex;
       justify-content: center;
       padding: 30px 0;
       
       input[type=file] {
           outline:0;
           opacity:0;
           pointer-events:none;
           user-select:none
       }
       .AvatarLabel {
           width:100%;
           border:2px dashed grey;
           border-radius:5px;
           display:block;
           padding:1.2em;
           transition:border 300ms ease;
           cursor:pointer;
           text-align:center
           
       }
       .AvatarLabel svg {
           padding-top: 30px;
           display:block;
           margin: 0 auto;
           width: 30%;
           height: 30%;
           padding-bottom:16px
       }
       .AvatarLabel .title {
            display:block;
            margin: 0 auto;
       }
       .AvatarLabel:hover {
           border:2px solid #000
           transition:200ms color
       }
       .AvatarLabel:hover {
           border:2px solid #000
       }
       .AvatarLabel:hover svg,
       .ChangeAvatarBlock .AvatarLabel:hover .title {
         color:#000
       }
       .status-input {
            text-align: center;
       }
       .uploadAvatar {
            display: block;
            margin: 0 auto;
            margin-top: 10px;
       }
       
	    
	}

    @media screen and (max-height: 600px) {
        .modal-view-wrapper {
            top: 0
        }
    }
`;

import styled from 'styled-components';

export const RequestFriendStyles: any = styled.div`
  margin-left: 30px;

  .list_request {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;
  }

  img {
    height: 100px;
    width: 100px;
    border-radius: 50%;
    margin-right: 30px;
  }

  .name {
    display: flex;
    flex-direction: column;

    span {
      font-size: 20px;
    }
  }

  .private_data {
    display: flex;
    align-items: center;
    font-weight: 600;
  }

  .accept {
    margin-right: 20px;
    background-color: ${({ theme: { colors } }) => colors.primary.brandColor};
  }

  .reject {
    background-color: #ffffff;
    border: 2px solid ${({ theme: { colors } }) => colors.primary.brandColor};
  }

  .icon {
    margin-right: 7px;
  }
`;

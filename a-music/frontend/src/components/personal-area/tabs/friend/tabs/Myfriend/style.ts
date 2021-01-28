import styled from 'styled-components';

export const MyFriendStyle: any = styled.div`
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;

  .friend {
    display: flex;
    align-items: center;
    border: 1px solid black;
    height: 250px;
    padding: 0 20px;
    width: 49%;
    min-width: 400px;
    margin: 20px 0;
  }
  .friend_delete {
    align-self: flex-end;
    background-color: ${({ theme: { colors } }) => colors.primary.brandColor};
    margin-top: auto;
    margin-bottom: 20px;
  }

  img {
    height: 200px;
    margin-right: 30px;
  }

  .info {
    display: flex;
    flex-direction: column;
    justify-content: center;
    width: 100%;
    height: 100%;
  }

  .name {
    margin-right: 10px;
  }

  .person_data {
    font-weight: 600;
    margin-top: auto;
  }
`;

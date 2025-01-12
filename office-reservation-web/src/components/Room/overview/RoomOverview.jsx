import Modal from './Modal.jsx';
import RoomDesigner from "../design/RoomDesigner.jsx";

const RoomOverviewModal = ({ isOpen, onClose, room, refreshRooms }) => {


    return (
        <Modal isOpen={isOpen} onClose={onClose} title="Room Overview">
            <RoomDesigner room={room} refreshRooms={refreshRooms}/>
        </Modal>
    );
};

export default RoomOverviewModal;


import Modal from './Modal.jsx';
import styles from "./RoomOverview.module.css";
import {generateRoomItems} from "../../../utils/generateRooms.js";

const RoomOverviewModal = ({ isOpen, onClose, capacity }) => {

    const roomItems = generateRoomItems(capacity)

    return (
        <Modal isOpen={isOpen} onClose={onClose} title="Room Overview">
            <div className={styles.roomFlexContainer}>
                {roomItems.map((item) => (
                    <div
                        key={item.id}
                        className={item.type === 'island' ? styles.island : styles.table}
                    >
                        {item.type === 'island' ? '8 seats' : '4 seats'}
                    </div>
                ))}
            </div>
        </Modal>
    );
};

export default RoomOverviewModal;


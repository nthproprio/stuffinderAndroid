package com.stuffinder.interfaces;

import com.stuffinder.data.Account;
import com.stuffinder.data.Profile;
import com.stuffinder.data.Tag;
import com.stuffinder.engine.NetworkServiceProvider;
import com.stuffinder.exceptions.AccountNotFoundException;
import com.stuffinder.exceptions.IllegalFieldException;
import com.stuffinder.exceptions.NetworkServiceException;
import com.stuffinder.exceptions.NotAuthenticatedException;

import java.util.List;

/**
 * This interface makes the link between the network service (in this case, this is the client web service) and the engine of the application.
 * It defines all the needed methods to use this service.
 * @author Nicolas Thierce
 *
 * @see NetworkServiceProvider
 */
public interface NetworkServiceInterface
{

    /**
     * Initialize this service to rend it available for network communications.
     * @throws NetworkServiceException if an error has occurred during the initialization.
     */
    public void initNetworkService() throws NetworkServiceException;


    /**
     * Create an account with the specified informations. Throws an exception if an error has occurred.
     * @param newAccount Object which contains all informations about the account to be created.
     * @param newPassword Password for the account to be created.
     * @throws IllegalFieldException If one field (i.e. one information) is illegal. <br/>
     * The possible fields with the reason(s) are : <br/>
     * <ul>
     * 		<li>{@link IllegalFieldException#PSEUDO Pseudo} (reasons {@link IllegalFieldException#REASON_VALUE_INCORRECT value incorrect} if the value is syntactically incorrect and {@link IllegalFieldException#REASON_VALUE_ALREADY_USED value already used} if the pseudo is already used for another account) </li>
     * 		<li>{@link IllegalFieldException#FIRSTNAME First name} (reason {@link IllegalFieldException#REASON_VALUE_INCORRECT value incorrect} if the value is syntactically incorrect) </li>
     * 		<li>{@link IllegalFieldException#LASTNAME Last name} (reason {@link IllegalFieldException#REASON_VALUE_INCORRECT value incorrect} if the value is syntactically incorrect) </li>
     * 		<li>{@link IllegalFieldException#EMAIL_ADDRESS Email address} (reason {@link IllegalFieldException#REASON_VALUE_INCORRECT value incorrect} if the value is incorrect) </li>
     * 		<li>{@link IllegalFieldException#PASSWORD Password} (reason {@link IllegalFieldException#REASON_VALUE_INCORRECT value incorrect} if the value is syntactically incorrect) </li>
     * </ul>
     *
     * @throws NetworkServiceException If a network service error has occurred.
     */
    public void createAccount(Account newAccount, String newPassword) throws IllegalFieldException, NetworkServiceException;


    /**
     * Do authentication with specified information.
     * @param pseudo The pseudo
     * @param password The password
     * @return The matching account if this operation succeeds.
     * @throws AccountNotFoundException If the couple (pseudo, password) doesn't exist.
     * @throws NetworkServiceException If a network service error has occurred.
     */
    public Account authenticate(String pseudo, String password) throws AccountNotFoundException, NetworkServiceException;

    /**
     * Logout.
     */
    public void logOut();

    /**
     * To get the current account.
     * @return The current account.
     * @throws NotAuthenticatedException If the authentication is not done.
     */
    public Account getCurrentAccount() throws NotAuthenticatedException;



    /**
     * Modify the email address of the current account.
     * @param emailAddress The new email address
     * @throws NotAuthenticatedException If the authentication is not done.
     * @throws IllegalFieldException If the specified email address is incorrect (field id {@link IllegalFieldException#EMAIL_ADDRESS EMAIL_ADDRESS} and reason {@link IllegalFieldException#REASON_VALUE_INCORRECT REASON_VALUE_INCORRECT}).
     * @throws NetworkServiceException If a network service error has occurred.
     */
    public void modifyEMailAddress(String emailAddress) throws NotAuthenticatedException, IllegalFieldException, NetworkServiceException;

    /**
     * Modify the password of the current account.
     * @param newPassword The new password
     * @throws NotAuthenticatedException If the authentication is not done.
     * @throws IllegalFieldException If the specified password is syntactically incorrect (field id {@link IllegalFieldException#PASSWORD PASSWORD} and reason {@link IllegalFieldException#REASON_VALUE_INCORRECT REASON_VALUE_INCORRECT}).
     * @throws NetworkServiceException If a network service error has occurred.
     */
    public void modifyPassword(String newPassword) throws NotAuthenticatedException, IllegalFieldException, NetworkServiceException;


    /**
     * To get the tags list of the current account.
     * @return the tags list if the authentication is already done successfully.
     * @throws NotAuthenticatedException If the authentication is not done.
     * @throws NetworkServiceException If a network service error has occurred.
     */
    public List<Tag> getTags() throws NotAuthenticatedException, NetworkServiceException;

    /**
     * Add a new tag to the current account.
     * @param tag The new tag to be added.
     * @return the tag added if this operation succeeds.
     * @throws NotAuthenticatedException If the authentication is not done.
     * @throws IllegalFieldException If one field (i.e. one information) is illegal. <br/>
     * The possible fields with the reason(s) are : <br/>
     * <ul>
     * 		<li>{@link IllegalFieldException#TAG_UID tag uid} (reasons {@link IllegalFieldException#REASON_VALUE_INCORRECT value incorrect} if the value is syntactically incorrect and {@link IllegalFieldException#REASON_VALUE_ALREADY_USED value already used} if the tag uid is already used) </li>
     * 		<li>{@link IllegalFieldException#TAG_OBJECT_NAME object name} (reasons {@link IllegalFieldException#REASON_VALUE_INCORRECT value incorrect} if the value is syntactically incorrect and {@link IllegalFieldException#REASON_VALUE_ALREADY_USED value already used} if the object name is already used for another tag) </li>
     * 		<li>{@link IllegalFieldException#TAG_OBJECT_IMAGE object image} (reason {@link IllegalFieldException#REASON_VALUE_INCORRECT value incorrect} if the filename is incorrect, i.e. if the file is not found or is not an image) </li>
     * </ul>
     * @throws NetworkServiceException If a network service error has occurred.
     */
    public Tag addTag(Tag tag) throws NotAuthenticatedException, IllegalFieldException, NetworkServiceException;

    /**
     * Modify the object name for a tag.
     * @param tag The tag to be modified.
     * @param newObjectName The new object name.
     * @return The tag modified if this operation succeeds.
     * @throws NotAuthenticatedException If the authentication is not done.
     * @throws IllegalFieldException If one field (i.e. one information) is illegal. <br/>
     * The possible fields with the reason(s) are : <br/>
     * <ul>
     * 		<li>{@link IllegalFieldException#TAG_UID tag uid} (reasons {@link IllegalFieldException#REASON_VALUE_INCORRECT value incorrect} if it is syntactically incorrect and {@link IllegalFieldException#REASON_VALUE_NOT_FOUND value not found} if the tag is not found) </li>
     * 		<li>{@link IllegalFieldException#TAG_OBJECT_NAME object name} (reasons {@link IllegalFieldException#REASON_VALUE_INCORRECT value incorrect} if the value is syntactically incorrect and {@link IllegalFieldException#REASON_VALUE_ALREADY_USED value already used} if this object name is already used for another tag) </li>
     * </ul>
     * @throws NetworkServiceException If a network service error has occurred.
     */
    public Tag modifyObjectName(Tag tag, String newObjectName) throws NotAuthenticatedException, IllegalFieldException, NetworkServiceException;

    /**
     * Modify the object image for a tag.
     * @param tag The tag to be modified.
     * @param newImageFileName the filename of the new object image.
     * @return The tag modified if this operation succeeds.
     * @throws NotAuthenticatedException If the authentication is not done.
     * @throws IllegalFieldException If one field (i.e. one information) is illegal. <br/>
     * The possible fields with the reason(s) are : <br/>
     * <ul>
     * 		<li>{@link IllegalFieldException#TAG_UID tag uid} (reasons {@link IllegalFieldException#REASON_VALUE_INCORRECT value incorrect} if it is syntactically incorrect and {@link IllegalFieldException#REASON_VALUE_NOT_FOUND value not found} if the tag is not found) </li>
     * 		<li>{@link IllegalFieldException#TAG_OBJECT_IMAGE object image} (reason {@link IllegalFieldException#REASON_VALUE_INCORRECT value incorrect} if the filename is incorrect, i.e. if the file is not found or is not an image) </li>
     * </ul>
     * @throws NetworkServiceException If a network service error has occurred.
     */
    public Tag modifyObjectImage(Tag tag, String newImageFileName) throws NotAuthenticatedException, IllegalFieldException, NetworkServiceException;

    /**
     * Remove a tag from the current account.
     * @param tag The tag to be removed.
     * @throws NotAuthenticatedException If the authentication is not done.
     * @throws IllegalFieldException If one field (i.e. one information) is illegal. <br/>
     * The possible fields with the reason(s) are : <br/>
     * <ul>
     * 		<li>{@link IllegalFieldException#TAG_UID tag uid} (reasons {@link IllegalFieldException#REASON_VALUE_INCORRECT value incorrect} if it is syntactically incorrect and {@link IllegalFieldException#REASON_VALUE_NOT_FOUND value not found} if the tag is not found) </li>
     * </ul>
     * @throws NetworkServiceException If a network service error has occurred.
     */
    public void removeTag(Tag tag) throws NotAuthenticatedException, IllegalFieldException, NetworkServiceException;



    /**
     * To create a new profile empty.
     * @param profileName The name of the new profile.
     * @return the created profile if this operation succeeds.
     * @throws NotAuthenticatedException If the authentication is not done.
     * @throws IllegalFieldException If the field {@link IllegalFieldException#PROFILE_NAME profile name} is incorrect. The reason can be {@link IllegalFieldException#REASON_VALUE_INCORRECT value incorrect} or {@link IllegalFieldException#REASON_VALUE_ALREADY_USED value already used}.
     * @throws NetworkServiceException If a network service error has occurred.
     */
    public Profile createProfile(String profileName) throws NotAuthenticatedException, IllegalFieldException, NetworkServiceException;


    /**
     * To add a tag to a profile.
     * @param profile The profile to be modified.
     * @param tag The tag to be added.
     * @return The profile modified if this operation succeeds.
     * @throws NotAuthenticatedException If the authentication is not done.
     * @throws IllegalFieldException
     * @throws NetworkServiceException If a network service error has occurred.
     */
    public Profile addTagToProfile(Profile profile, Tag tag) throws NotAuthenticatedException, IllegalFieldException, NetworkServiceException;


    /**
     *
     * @param profile
     * @param tags
     * @return
     * @throws NotAuthenticatedException
     * @throws IllegalFieldException
     * @throws NetworkServiceException
     */
    public Profile addTagsToProfile(Profile profile, List<Tag> tags) throws NotAuthenticatedException, IllegalFieldException, NetworkServiceException;

    /**
     * SPECIFICATION NOT FINISHED.
     */
    public Profile removeTagFromProfile(Profile profile, Tag tag) throws NotAuthenticatedException, IllegalFieldException, NetworkServiceException;

    /**
     * SPECIFICATION NOT FINISHED.
     */
    public Profile removeAllFromProfile(Profile profile) throws NotAuthenticatedException, IllegalFieldException, NetworkServiceException;

    /**
     * SPECIFICATION NOT FINISHED.
     */
    public Profile replaceTagListOfProfile(Profile profile, List<Tag> tagList) throws NotAuthenticatedException, IllegalFieldException, NetworkServiceException;

    /**
     * SPECIFICATION NOT FINISHED.
     */
    public Profile replaceTagListOfProfile(Profile profile, Tag[] tagList) throws NotAuthenticatedException, IllegalFieldException, NetworkServiceException;

    /**
     * SPECIFICATION NOT FINISHED.
     */
    public Profile getProfile(String profileName) throws NotAuthenticatedException;

    /**
     * To get the profiles of the current account.
     * @return The profiles list if the authentication is already done successfully.
     * @throws NotAuthenticatedException If the authentication is not done.
     * @throws NetworkServiceException If a network service error has occurred.
     */
    public List<Profile> getProfiles() throws NotAuthenticatedException, NetworkServiceException;
}